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
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.QPanel;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.response.batch.reader.QuerydslNoOffsetPagingItemReader;
import surveasy.domain.response.batch.reader.expression.Expression;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetNumberOptions;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class AggregationDonePanelJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;
    private static final QPanel qPanel = QPanel.panel;
    private static final int PANEL_CHUNK_SIZE = 100;

    @Bean
    public Job aggregationDonePanelJob() {
        return new JobBuilder("aggregationDonePanelJob", jobRepository)
                .start(panelUpdateStep())
                .build();
    }


    @Bean
    protected Step panelUpdateStep() {
        return new StepBuilder("panelUpdateStep", jobRepository)
                .<Panel, Panel>chunk(PANEL_CHUNK_SIZE, transactionManager)
                .reader(querydslNoOffsetPagingPanelReader2())
                .writer(jdbcBatchPanelWriter2())
                .build();
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Panel> querydslNoOffsetPagingPanelReader2() {
        QuerydslNoOffsetNumberOptions<Panel, Long> options = new QuerydslNoOffsetNumberOptions<>(qPanel.id, Expression.ASC);
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                PANEL_CHUNK_SIZE,
                options,
                jpaQueryFactory -> jpaQueryFactory
                        .selectFrom(qPanel)
                        .where(qPanel.deletedAt.isNull()
                                .and(qPanel.status.eq(PanelStatus.FS_DONE))
                                .and(qPanel.rewardTemp.gt(0))
                        )
        );
    }

    @Bean
    public JdbcBatchItemWriter<Panel> jdbcBatchPanelWriter2() {
        return new JdbcBatchItemWriterBuilder<Panel>()
                .dataSource(dataSource)
                .sql("UPDATE panel SET reward_current = reward_current - :rewardTemp, reward_total = reward_total + :rewardTemp, reward_temp = 0 WHERE id = :id")
                .beanMapped()
                .build();
    }
}
