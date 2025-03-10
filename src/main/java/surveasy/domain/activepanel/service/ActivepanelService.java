package surveasy.domain.activepanel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.activepanel.dto.ActivePanelResponse;
import surveasy.domain.activepanel.helper.ActivepanelHelper;
import surveasy.domain.activepanel.mapper.ActivepanelMapper;

@RequiredArgsConstructor
@Transactional
@Service
public class ActivepanelService {

    private final ActivepanelHelper activepanelHelper;
    private final ActivepanelMapper activepanelMapper;

    public ActivePanelResponse getActivePanel() {
        Activepanel activePanel = activepanelHelper.getActivePanel();
        return activepanelMapper.toActivePanelResponse(activePanel);
    }
}
