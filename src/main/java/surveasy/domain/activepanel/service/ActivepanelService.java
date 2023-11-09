package surveasy.domain.activepanel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.activepanel.dto.ActivePanelResponse;
import surveasy.domain.activepanel.helper.ActivepanelHelper;
import surveasy.domain.activepanel.mapper.ActivepanelMapper;

@Service
@RequiredArgsConstructor
public class ActivepanelService {

    private final ActivepanelHelper activepanelHelper;
    private final ActivepanelMapper activepanelMapper;

    @Transactional
    public ActivePanelResponse getActivePanel() {
        return activepanelMapper.toActivePanelResponse(activepanelHelper.getActivePanel());
    }
}
