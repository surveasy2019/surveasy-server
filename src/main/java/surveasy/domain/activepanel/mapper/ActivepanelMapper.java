package surveasy.domain.activepanel.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.activepanel.dto.ActivePanelResponse;

@Component
public class ActivepanelMapper {

    public Activepanel toEntity(String totalList, String maleList, String femaleList) {
        return Activepanel.of(totalList, maleList, femaleList);
    }

    public ActivePanelResponse toActivePanelResponse(Activepanel activePanel) {
        return ActivePanelResponse.of(activePanel.getTotalList(), activePanel.getMaleList(), activePanel.getFemaleList());
    }
}
