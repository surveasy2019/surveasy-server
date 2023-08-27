package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
//import surveasy.global.config.jwt.TokenProvider;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PanelService {

//    private final TokenProvider tokenProvider;
    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;

    @Transactional
    public Panel addExistingPanel(PanelUidDTO panelUidDTO) throws ParseException, ExecutionException, InterruptedException {
        return panelHelper.addExistingPanel(panelUidDTO);
    }

}
