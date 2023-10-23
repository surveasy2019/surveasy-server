package surveasy.domain.activepanel.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.helper.ActivepanelHelper;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ActivePanelScheduler {

    private final PanelHelper panelHelper;
    private final ActivepanelHelper activepanelHelper;

    @Scheduled(cron = "0 0 3 10/10 * ?")     // 매달 10일을 시작으로 10일 단위로 [03:00:00 am]마다 실행
//    @Scheduled(cron = "1/3 * * * * ?")
    public void createActivePanel() {
        //        System.out.println("ActivePanelScheduler.createActivePanel----------------------------------------- " + new Date());

        List<Panel> activePanelList = panelHelper.getActivePanelList();

        /*
        * 전체 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 남성 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * 여성 (2024, 2529, 3034, 3539, 4044, 4549, 50, 60)
        * ActivePanel Entity 저장
        * */

    }
}
