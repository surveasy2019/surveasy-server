package surveasy.domain.activepanel.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.activepanel.repository.ActivepanelRepository;
import surveasy.domain.panel.helper.PanelHelper;

@Component
@RequiredArgsConstructor
public class ActivePanelScheduler {
    private final PanelHelper panelHelper;
    private final ActivepanelRepository activepanelRepository;

    @Scheduled(cron = "0 0 3 1,11,21 * ?")
    public void createActivePanel() {
        Activepanel activepanel = panelHelper.getActivePanelList();
        activepanelRepository.save(activepanel);
        /*
        * 전체 (총합, 2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 남성 (총합, 2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 여성 (총합, 2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * ActivePanel Entity 저장
        * */
    }
}
