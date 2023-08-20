package surveasy.domain.panel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.PanelTokenResponse;

@Slf4j
@RestController
@RequestMapping("/panel")
@RequiredArgsConstructor
public class PanelController {

//    @PostMapping("/auth")
//    public PanelTokenResponse panelAuth(PanelUidDTO panelUidDTO) {
//
//    }
}
