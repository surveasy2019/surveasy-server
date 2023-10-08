package surveasy.domain.panel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.PanelAdminListResponse;
import surveasy.domain.panel.dto.response.PanelInfoResponse;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.service.PanelService;
import surveasy.global.config.user.PanelDetails;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/panel")
@RequiredArgsConstructor
@Tag(name = "Panel")
public class PanelController {

    private final PanelService panelService;


    @PostMapping("/auth/existing")
    @Operation(summary = "App 기존 패널 정보 가져와서 가입시키기")
    public PanelTokenResponse signUpExisting(@RequestBody @Valid PanelUidDTO panelUidDTO) throws ExecutionException, InterruptedException, ParseException {
        return panelService.signUpExisting(panelUidDTO);
    }

    @PostMapping("/auth/new")
    @Operation(summary = "App 새로운 패널 가입시키기")
    public PanelTokenResponse signUpNew(@RequestBody PanelSignUpDTO panelSignUpDTO) {
        return panelService.signUpNew(panelSignUpDTO);
    }

    @GetMapping("")
    @Operation(summary = "App 패널 정보 불러오기")
    public PanelInfoResponse getPanelInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.getPanelInfo(panelDetails);
    }

    @GetMapping("/admin/list")
    @Operation(summary = "Admin 패널 리스트")
    public PanelAdminListResponse getAdminPanelList(@PageableDefault(size = 50) Pageable pageable) {
        return panelService.getAdminPanelList(pageable);
    }
}
