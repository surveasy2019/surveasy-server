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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import surveasy.domain.response.batch.reader.QuerydslNoOffsetPagingItemReader;
import surveasy.domain.response.batch.reader.QuerydslPagingItemReader;
import surveasy.domain.response.batch.reader.expression.Expression;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetNumberOptions;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.response.vo.ResponseBatchVo;
import surveasy.domain.response.domain.QResponse;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class ResponseJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    private static final QResponse qResponse = QResponse.response;

    private static final LocalDate now = LocalDate.now();
    private static final int SENT_CYCLE = (now.getDayOfMonth() == 1) ? 11 : 10;
    private static final int CHUNK_SIZE = 5;

    @Bean
    public Job aggregateResponseJob() {
        return new JobBuilder("calculateResponseJob", jobRepository)
                .start(step1())
//                .next(step2())
                .build();
    }

    @Bean
    protected Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Response, ResponseBatchVo>chunk(CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingItemReader())
                .processor(jpaPagingResponseProcessor())
                .writer(jpaPagingResponseWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Response> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Response>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT r FROM Response r WHERE status IN ('CREATED', 'UPDATED') ORDER BY id ASC")
                .build();
    }

    @Bean
    public QuerydslPagingItemReader<Response> querydslPagingItemReader() {
        System.out.println(now.minusDays(SENT_CYCLE).atTime(0, 0) + "      " + now.atTime(0, 0));

        return new QuerydslPagingItemReader<>(
                entityManagerFactory,
                CHUNK_SIZE,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.in(ResponseStatus.CREATED, ResponseStatus.UPDATED)
                                .and(qResponse.createdAt.between(
                                        now.minusDays(SENT_CYCLE).atTime(0, 0),
                                        now.atTime(0, 0))))
        );
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Response> querydslNoOffsetPagingItemReader() {
        QuerydslNoOffsetNumberOptions<Response, Long> options = new QuerydslNoOffsetNumberOptions<>(qResponse.id, Expression.ASC);
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                CHUNK_SIZE,
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
    public ItemWriter<ResponseBatchVo> jpaPagingResponseWriter() {
        return list -> {
            for (ResponseBatchVo responseBatchVo : list) {
                System.out.println("_______________________________ Current Response : " + responseBatchVo.getId() + " " + responseBatchVo.getReward());
            }
        };
    }
}
