package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PanelService {

    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;

    @Transactional
    public Panel addExistingPanel(PanelUidDTO panelUidDTO) throws ParseException, ExecutionException, InterruptedException {
        return panelHelper.addExistingPanel(panelUidDTO);
    }

}
