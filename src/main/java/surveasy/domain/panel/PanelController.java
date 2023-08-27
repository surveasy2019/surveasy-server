package surveasy.domain.panel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.service.PanelService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/panel")
@RequiredArgsConstructor
@Tag(name = "Panel")
public class PanelController {

    private final PanelService panelService;
    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;


    @PostMapping("/auth")
    @Operation(summary = "App 기존 패널 가입시키기")
    public Panel addExistingPanel(@RequestBody @Valid PanelUidDTO panelUidDTO) throws ExecutionException, InterruptedException, ParseException {
        return panelService.addExistingPanel(panelUidDTO);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("------------------------------------------------------------- PanelController.test");
        return ResponseEntity.ok("TTTTTTTTTTest");
    }

}
