package surveasy.domain.activepanel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import surveasy.domain.activepanel.dto.ActivePanelResponse;
import surveasy.domain.activepanel.service.ActivepanelService;

@Slf4j
@RestController
@RequestMapping("/activepanel")
@RequiredArgsConstructor
@Tag(name = "Activepanel")
public class ActivepanelController {

    private final ActivepanelService activepanelService;

    @GetMapping
    @Operation(summary = "Active Panel 현황")
    public ActivePanelResponse getActivePanel() {
        return activepanelService.getActivePanel();
    }

}
