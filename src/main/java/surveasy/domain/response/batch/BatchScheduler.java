package surveasy.domain.response.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import surveasy.domain.response.service.FileService;
import surveasy.global.common.util.EmailUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final AggregationJobConfig aggregationJobConfig;
    private final FileService fileService;
    private final EmailUtils emailUtils;

    @Scheduled(cron = "0 10 4 1,11,21 * ?")
    public void batchScheduler() throws Exception {
        fileService.deleteAllFiles();
        jobLauncher.run(aggregationJobConfig.aggregationJob(),
                new JobParametersBuilder()
                        .addLong("time", new Date().getTime())
                        .toJobParameters()
        );
        emailUtils.sendCsvMail();
    }
}
