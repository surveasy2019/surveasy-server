package surveasy.domain.response.batch;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
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
    private static final int RESPONSE_CHUNK_SIZE = 100;

    @Bean
    public Job aggregationDoneResponseJob() {
        return new JobBuilder("aggregationDoneResponseJob", jobRepository)
                .start(responseUpdateStep(null))
                .build();
    }

    @Bean
    @JobScope
    protected Step responseUpdateStep(@Value("#{jobParameters[now]}") LocalDate now) {
        return new StepBuilder("responseUpdateStep", jobRepository)
                .<Response, Response>chunk(RESPONSE_CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingResponseReader2(now))
                .writer(jdbcBatchResponseWriter2(now))
                .build();
    }

    @Bean
    @StepScope
    public QuerydslNoOffsetPagingItemReader<Response> querydslNoOffsetPagingResponseReader2(@Value("#{jobParameters[now]}") LocalDate now) {
        int sentCycle = (now.getDayOfMonth() == 1) ? 11 : 10;

        QResponse qResponse = QResponse.response;
        QuerydslNoOffsetNumberOptions<Response, Long> options = new QuerydslNoOffsetNumberOptions<>(qResponse.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                RESPONSE_CHUNK_SIZE,
                options,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.eq(ResponseStatus.WAITING)
                                .and(qResponse.createdAt.between(
                                        now.minusDays(sentCycle).atTime(0, 0),
                                        now.atTime(0, 0))))
        );
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Response> jdbcBatchResponseWriter2(@Value("#{jobParameters[now]}") LocalDate now) {
        return new JdbcBatchItemWriterBuilder<Response>()
                .dataSource(dataSource)
                .sql("UPDATE response SET status = 'DONE', sent_at = \'" + now + "\' WHERE id = :id")
                .beanMapped()
                .build();
    }
}
