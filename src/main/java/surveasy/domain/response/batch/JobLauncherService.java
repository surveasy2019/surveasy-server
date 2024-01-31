package surveasy.domain.response.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobLauncherService {

    private final JobLauncher jobLauncher;
    private final Job responseJob;
    private final Job panelJob;

    @Autowired
    public JobLauncherService(JobLauncher jobLauncher,
                              @Qualifier("aggregationDoneResponseJob") Job responseJob,
                              @Qualifier("aggregationDonePanelJob") Job panelJob) {
        this.jobLauncher = jobLauncher;
        this.responseJob = responseJob;
        this.panelJob = panelJob;
    }

    public void runJobs() {
        try {
            final JobExecution responseJobExecution = jobLauncher.run(responseJob,
                    new JobParametersBuilder()
                        .addLong("time", new Date().getTime())
                        .toJobParameters());

            final JobExecution panelJobExecution = jobLauncher.run(panelJob,
                    new JobParametersBuilder()
                            .addLong("time", new Date().getTime())
                            .toJobParameters());

            System.out.println("Job completed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
}
