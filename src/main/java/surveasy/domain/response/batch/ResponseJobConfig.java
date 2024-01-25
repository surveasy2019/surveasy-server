package surveasy.domain.response.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import surveasy.domain.response.repository.ResponseRepository;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ResponseJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private static final int CHUNK_SIZE = 20;

    @Bean
    public Job calculateResponseJob() {
        return new JobBuilder("calculateResponseJob", jobRepository)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    protected Step step1() {
        return new StepBuilder("step1", jobRepository)
                .chunk(CHUNK_SIZE, transactionManager)
                .build();
    }

    @Bean
    protected Step step2() {
        return new StepBuilder("step2", jobRepository)
                .chunk(CHUNK_SIZE, transactionManager)
                .build();
    }

}
