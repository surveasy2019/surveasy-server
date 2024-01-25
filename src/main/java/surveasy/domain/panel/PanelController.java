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
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.service.PanelService;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;
import surveasy.global.config.user.PanelDetails;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/panel")
@RequiredArgsConstructor
@Tag(name = "Panel")
public class PanelController {

    private final PanelService panelService;

    @PostMapping("/signin")
    @Operation(summary = "App 기존 패널 email 로그인")
    public PanelTokenResponse signInEmail(@RequestBody @Valid PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException {
        return panelService.signInEmail(panelEmailSignInDTO);
    }

    @PostMapping("/oauth2")
    @Operation(summary = "App 소셜 로그인 후 회원가입 여부 판단 & 토큰 발급")
    public OAuth2Response oauth2(@RequestBody @Valid OAuth2UserInfo oAuth2UserInfo) {
        return panelService.oauth2(oAuth2UserInfo);
    }

    @PostMapping("/signup")
    @Operation(summary = "App 신규 패널 추가 정보 입력")
    public PanelTokenResponse signUp(@AuthenticationPrincipal PanelDetails panelDetails,
                                  @RequestBody @Valid PanelSignUpDTO panelSignUpDTO) {
        return panelService.signUp(panelDetails, panelSignUpDTO);
    }

    @PostMapping("/first-survey")
    @Operation(summary = "App 패널 First Survey")
    public PanelIdResponse doFirstSurvey(@AuthenticationPrincipal PanelDetails panelDetails,
                                         @RequestBody @Valid PanelFirstSurveyDTO panelFirstSurveyDTO) {
        return panelService.doFirstSurvey(panelDetails, panelFirstSurveyDTO);
    }

    @PostMapping("/reissue")
    @Operation(summary = "App 엑세스 토큰 재발급")
    public PanelTokenResponse reissueAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return panelService.reissueToken(refreshTokenRequestDTO);
    }

    @GetMapping("/reissue/{panelId}")
    @Operation(summary = "임시 엑세스 토큰 재발급 - panelId")
    public ResponseEntity<String> userIdReissue(@PathVariable Long panelId) {
        return ResponseEntity.ok(panelService.reissueAccessToken(panelId));
    }

    @GetMapping("/signout")
    @Operation(summary = "App 패널 로그아웃")
    public void signOut(@AuthenticationPrincipal PanelDetails panelDetails) {
        panelService.signOut(panelDetails);
    }

    @DeleteMapping ("/withdraw")
    @Operation(summary = "App 패널 탈퇴")
    public PanelAuthProviderResponse withdraw(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.withdraw(panelDetails);
    }

    @GetMapping("/home")
    @Operation(summary = "App 홈화면 패널 정보 불러오기")
    public PanelHomeInfoResponse getPanelHomeInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return panelService.getPanelHomeInfo(panelDetails);
    }

    @GetMapping("/response")
    @Operation(summary = "App 마이페이지 정산 예정 금액 & 계좌 정보")
    public ResponseEntity<PanelResponseInfoVo> getPanelMyPageResponseInfo(@AuthenticationPrincipal PanelDetails panelDetails) {
        return ResponseEntity.ok(panelService.getPanelResponseInfoVo(panelDetails));
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
