package surveasy.domain.response.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final AggregationJobConfig aggregationJobConfig;

    @Scheduled(fixedDelay = 10000000)
    public void batchScheduler() throws Exception {
        jobLauncher.run(aggregationJobConfig.aggregateResponseJob(),
                new JobParametersBuilder()
                        .addLong("time", new Date().getTime())
                        .toJobParameters()
        );
    }
}
