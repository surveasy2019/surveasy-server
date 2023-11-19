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
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.panel.dto.request.PanelInfoUpdateDTO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.helper.PanelHelper;
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

    @GetMapping("/auth/token/{panelId}")
    @Operation(summary = "임시 - accessToken 재발행")
    public String reissueAccessToken(@PathVariable Long panelId) {
        return panelService.reissueAccessToken(panelId);
    }

    @GetMapping("")
    @Operation(summary = "App 홈화면 패널 정보 불러오기")
    public PanelHomeInfoResponse getPanelHomeInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.getPanelHomeInfo(panelDetails);
    }

    @GetMapping("/mypage")
    @Operation(summary = "App 마이페이지 패널 정보 불러오기")
    public PanelMyPageInfoResponse getPanelMyPageInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.getPanelMyPageInfo(panelDetails);
    }

    @PatchMapping("/mypage/update")
    @Operation(summary = "App 마이페이지 패널 정보 수정하기")
    public PanelMyPageInfoResponse updatePanelInfo(@AuthenticationPrincipal PanelDetails panelDetails,
                                           @RequestBody PanelInfoUpdateDTO panelInfoUpdateDTO) {
        return panelService.updatePanelInfo(panelDetails, panelInfoUpdateDTO);
    }

    @GetMapping("/admin/list")
    @Operation(summary = "Admin 패널 리스트")
    public PanelAdminListResponse getAdminPanelList(@PageableDefault(size = 50) Pageable pageable) {
        return panelService.getAdminPanelList(pageable);
    }
}
