package surveasy.domain.panel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.dto.request.PanelInfoUpdateDTO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelExistingDTO;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.service.PanelService;
import surveasy.domain.panel.vo.PanelInfoVo;
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

    @PostMapping("/signup/existing")
    @Operation(summary = "App 기존 패널 회원 가입")
    public PanelTokenResponse signUpExisting(@RequestBody @Valid PanelExistingDTO panelExistingDTO) throws ExecutionException, InterruptedException, ParseException {
        return panelService.signUpExisting(panelExistingDTO);
    }

    @PostMapping("/signup")
    @Operation(summary = "App 신규 패널 회원 가입")
    public PanelTokenResponse signUpNew(@RequestBody PanelSignUpDTO panelSignUpDTO) {
        return panelService.signUpNew(panelSignUpDTO);
    }

    @GetMapping("/auth/token/{panelId}")
    @Operation(summary = "임시 - accessToken 재발행")
    public String reissueAccessToken(@PathVariable Long panelId) {
        return panelService.reissueAccessToken(panelId);
    }

    @GetMapping("/signout")
    @Operation(summary = "App 패널 로그아웃")
    public void signOut(@AuthenticationPrincipal PanelDetails panelDetails) {
        panelService.signOut(panelDetails);
    }

    @GetMapping("/withdraw")
    @Operation(summary = "App 패널 탈퇴")
    public void withdraw(@AuthenticationPrincipal PanelDetails panelDetails) {
        panelService.withdraw(panelDetails);
    }

    @GetMapping("/home")
    @Operation(summary = "App 홈화면 패널 정보 불러오기")
    public PanelHomeInfoResponse getPanelHomeInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.getPanelHomeInfo(panelDetails);
    }

    @GetMapping("")
    @Operation(summary = "App 마이페이지 패널 정보 불러오기")
    public ResponseEntity<PanelInfoVo> getPanelMyPageInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return ResponseEntity.ok(panelService.getPanelInfoVo(panelDetails));
    }

    @PatchMapping("")
    @Operation(summary = "App 마이페이지 패널 정보 수정하기")
    public ResponseEntity<PanelInfoVo> updatePanelInfo(
            @AuthenticationPrincipal PanelDetails panelDetails,
            @RequestBody PanelInfoUpdateDTO panelInfoUpdateDTO) {
        return ResponseEntity.ok(panelService.updatePanelInfo(panelDetails, panelInfoUpdateDTO));
    }

    @GetMapping("/admin")
    @Operation(summary = "Admin 패널 리스트")
    public PanelAdminListResponse getAdminPanelList(@PageableDefault(size = 50) Pageable pageable) {
        return panelService.getAdminPanelList(pageable);
    }
}
