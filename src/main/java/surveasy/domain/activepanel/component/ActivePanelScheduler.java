package surveasy.domain.activepanel.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.repository.ActivepanelRepository;
import surveasy.domain.panel.helper.PanelHelper;

@Component
@RequiredArgsConstructor
public class ActivePanelScheduler {

    private final PanelHelper panelHelper;
    private final ActivepanelRepository activepanelRepository;

    @Scheduled(cron = "0 0 3 1,11,21 * ?")     // 매달 1,11,21일 [03:00:00 am]마다 실행
    public void createActivePanel() {

        activepanelRepository.save(panelHelper.getActivePanelList());

        /*
        * 전체 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 남성 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 여성 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * ActivePanel Entity 저장
        * */

    }
}
