package surveasy.domain.response.batch;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import surveasy.domain.response.batch.reader.QuerydslNoOffsetPagingItemReader;
import surveasy.domain.response.batch.reader.expression.Expression;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetNumberOptions;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;

import javax.sql.DataSource;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class AggregationDoneResponseJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private static final QResponse qResponse = QResponse.response;

    private static final LocalDate now = LocalDate.now();
    private static final int SENT_CYCLE = (now.getDayOfMonth() == 1) ? 11 : 10;
    private static final int RESPONSE_CHUNK_SIZE = 5;

    @Bean
    public Job aggregationDoneResponseJob() {
        return new JobBuilder("aggregationDoneResponseJob", jobRepository)
                .start(responseUpdateStep())
                .build();
    }

    @Bean
    protected Step responseUpdateStep() {
        return new StepBuilder("responseUpdateStep", jobRepository)
                .<Response, Response>chunk(RESPONSE_CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingResponseReader2())
                .writer(jdbcBatchResponseWriter2())
                .build();
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Response> querydslNoOffsetPagingResponseReader2() {
        QuerydslNoOffsetNumberOptions<Response, Long> options = new QuerydslNoOffsetNumberOptions<>(qResponse.id, Expression.ASC);
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                RESPONSE_CHUNK_SIZE,
                options,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.eq(ResponseStatus.WAITING)
                                .and(qResponse.createdAt.between(
                                        now.minusDays(SENT_CYCLE).atTime(0, 0),
                                        now.atTime(0, 0))))
        );
    }

    @Bean
    public JdbcBatchItemWriter<Response> jdbcBatchResponseWriter2() {
        return new JdbcBatchItemWriterBuilder<Response>()
                .dataSource(dataSource)
                .sql("UPDATE response SET status = 'DONE' WHERE id = :id")
                .beanMapped()
                .build();
    }
}
