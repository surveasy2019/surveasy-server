package surveasy.domain.response.batch;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.vo.PanelBatchVo;
import surveasy.domain.response.batch.reader.QuerydslNoOffsetPagingItemReader;
import surveasy.domain.response.batch.reader.QuerydslPagingItemReader;
import surveasy.domain.response.batch.reader.expression.Expression;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetNumberOptions;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetOptions;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.response.vo.ResponseBatchVo;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.panel.domain.QPanel;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class AggregationJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private static final QResponse qResponse = QResponse.response;
    private static final QPanel qPanel = QPanel.panel;

    private static final LocalDate now = LocalDate.now();
    private static final int SENT_CYCLE = (now.getDayOfMonth() == 1) ? 11 : 10;
    private static final int RESPONSE_CHUNK_SIZE = 5;
    private static final int PANEL_CHUNK_SIZE = 2;
    private static final String CSV_FILE_PATH = "output/" + LocalDate.now() + ".csv";

    @Bean
    public Job aggregateResponseJob() throws Exception {
        return new JobBuilder("aggregationJob", jobRepository)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    protected Step step1() {
        return new StepBuilder("queryStep", jobRepository)
                .<Response, ResponseBatchVo>chunk(RESPONSE_CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingResponseReader())
                .processor(jpaPagingResponseProcessor())
                .writer(compositePanelAndResponseWriter())
                .build();
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Response> querydslNoOffsetPagingResponseReader() {
        QuerydslNoOffsetNumberOptions<Response, Long> options = new QuerydslNoOffsetNumberOptions<>(qResponse.id, Expression.ASC);
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                RESPONSE_CHUNK_SIZE,
                options,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.in(ResponseStatus.CREATED, ResponseStatus.UPDATED)
                                .and(qResponse.createdAt.between(
                                        now.minusDays(SENT_CYCLE).atTime(0, 0),
                                        now.atTime(0, 0))))
        );
    }

    @Bean
    public ItemProcessor<Response, ResponseBatchVo> jpaPagingResponseProcessor() {
        return ResponseBatchVo::from;
    }

    @Bean
    public CompositeItemWriter<ResponseBatchVo> compositePanelAndResponseWriter() {
        final CompositeItemWriter<ResponseBatchVo> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(jdbcBatchPanelWriter(), jdbcBatchResponseWriter()));
        return compositeItemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<ResponseBatchVo> jdbcBatchPanelWriter() {
        return new JdbcBatchItemWriterBuilder<ResponseBatchVo>()
                .dataSource(dataSource)
                .sql("UPDATE panel SET reward_temp = reward_temp + :reward WHERE id = :panelId")
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<ResponseBatchVo> jdbcBatchResponseWriter() {
        return new JdbcBatchItemWriterBuilder<ResponseBatchVo>()
                .dataSource(dataSource)
                .sql("UPDATE response SET status = 'WAITING' WHERE id = :id")
                .beanMapped()
                .build();
    }

    @Bean
    protected Step step2() throws Exception {
        return new StepBuilder("csvFileExportStep", jobRepository)
                .<Panel, PanelBatchVo>chunk(PANEL_CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingPanelReader())
                .processor(jpaPagingPanelProcessor())
                .writer(csvFileWriter())
                .build();
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Panel> querydslNoOffsetPagingPanelReader() {
        QuerydslNoOffsetNumberOptions<Panel, Long> options = new QuerydslNoOffsetNumberOptions<>(qPanel.id, Expression.ASC);
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                PANEL_CHUNK_SIZE,
                options,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qPanel)
                        .where(qPanel.rewardTemp.gt(0).and(qPanel.deletedAt.isNull()))
        );
    }

    @Bean
    public ItemProcessor<Panel, PanelBatchVo> jpaPagingPanelProcessor() {
        return PanelBatchVo::from;
    }

    @Bean
    public CompositeItemWriter<PanelBatchVo> compositePanelWriter() throws Exception {
        final CompositeItemWriter<PanelBatchVo> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(csvFileWriter()));
        return compositeItemWriter;
    }

    @Bean
    public FlatFileItemWriter<PanelBatchVo> csvFileWriter() throws Exception {
        BeanWrapperFieldExtractor<PanelBatchVo> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"accountType", "accountNumber", "rewardTemp", "accountOwner", "sender"});    // of PanelBatchVo

        DelimitedLineAggregator<PanelBatchVo> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(extractor);

        FlatFileItemWriter<PanelBatchVo> writer = new FlatFileItemWriterBuilder<PanelBatchVo>()
                .name("csvFileWriter")
                .encoding("UTF-8")
                .resource(new FileSystemResource(CSV_FILE_PATH))
                .lineAggregator(lineAggregator)
                .headerCallback(writer1 -> writer1.write("입금은행,입금계좌번호,입금액,예상예금주,입금통장표시"))
                .append(true)
                .build();

        writer.afterPropertiesSet();
        return writer;
    }


    /*
    @Bean
    public JpaPagingItemReader<Response> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Response>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(RESPONSE_CHUNK_SIZE)
                .queryString("SELECT r FROM Response r WHERE status IN ('CREATED', 'UPDATED') ORDER BY id ASC")
                .build();
    }

    @Bean
    public QuerydslPagingItemReader<Response> querydslPagingItemReader() {
        return new QuerydslPagingItemReader<>(
                entityManagerFactory,
                RESPONSE_CHUNK_SIZE,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.in(ResponseStatus.CREATED, ResponseStatus.UPDATED)
                                .and(qResponse.createdAt.between(
                                        now.minusDays(SENT_CYCLE).atTime(0, 0),
                                        now.atTime(0, 0))))
        );
    }

    @Bean
    public ItemWriter<ResponseBatchVo> jpaPagingResponseWriter() {
        return list -> {
            for (ResponseBatchVo responseBatchVo : list) {
                System.out.println("########## Current Response : " + responseBatchVo.getId() + " " + responseBatchVo.getReward());
            }
        };
    }
    */
}
