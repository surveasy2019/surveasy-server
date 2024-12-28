package surveasy.domain.panel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.service.PanelService;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;
import surveasy.global.common.annotation.CurrentPanel;

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

    @PostMapping("/oauth2/apple")
    @Operation(summary = "App 애플 소셜 로그인 후 회원가입 여부 판단")
    public OAuth2AppleResponse oauth2Apple(@RequestBody @Valid AuthIdDTO oauthIdDTO) {
        return panelService.oauth2Apple(oauthIdDTO);
    }

    @PostMapping("/signup")
    @Operation(summary = "App 신규 패널 추가 정보 입력")
    public PanelTokenResponse signUp(@CurrentPanel Panel panel,
                                     @RequestBody @Valid PanelSignUpDTO panelSignUpDTO) {
        return panelService.signUp(panel, panelSignUpDTO);
    }

    @PostMapping("/signup/apple")
    @Operation(summary = "App 애플 로그인 신규 패널 회원가입")
    public PanelTokenResponse signUpApple(@RequestBody @Valid PanelAppleSignUpDTO panelAppleSignUpDTO) {
        return panelService.signUpApple(panelAppleSignUpDTO);
    }

    @PostMapping("/first-survey")
    @Operation(summary = "App 패널 First Survey")
    public PanelIdResponse doFirstSurvey(@CurrentPanel Panel panel,
                                         @RequestBody @Valid PanelFirstSurveyDTO panelFirstSurveyDTO) {
        return panelService.doFirstSurvey(panel, panelFirstSurveyDTO);
    }

    @PostMapping("/reissue")
    @Operation(summary = "App 엑세스 토큰 재발급")
    public PanelTokenResponse reissueAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return panelService.reissueToken(refreshTokenRequestDTO);
    }

    @GetMapping("/signout")
    @Operation(summary = "App 패널 로그아웃")
    public void signOut(@CurrentPanel Panel panel) {
        panelService.signOut(panel);
    }

    @DeleteMapping ("/withdraw")
    @Operation(summary = "App 패널 탈퇴")
    public PanelAuthProviderResponse withdraw(@CurrentPanel Panel panel) {
        return panelService.withdraw(panel);
    }

    @GetMapping("/home")
    @Operation(summary = "App 홈화면 패널 정보 불러오기")
    public PanelHomeInfoResponse getPanelHomeInfo(@CurrentPanel Panel panel) {
        return panelService.getPanelHomeInfo(panel);
    }

    @GetMapping("/response")
    @Operation(summary = "App 마이페이지 정산 예정 금액 & 계좌 정보")
    public ResponseEntity<PanelResponseInfoVo> getPanelMyPageResponseInfo(@CurrentPanel Panel panel) {
        return ResponseEntity.ok(panelService.getPanelResponseInfoVo(panel));
    }


    @GetMapping("")
    @Operation(summary = "App 마이페이지 패널 정보 불러오기")
    public ResponseEntity<PanelInfoVo> getPanelMyPageInfo(@CurrentPanel Panel panel) {
        return ResponseEntity.ok(panelService.getPanelInfoVo(panel));
    }

    @GetMapping("/watermark")
    @Operation(summary = "App 워터마크 패널 정보 불러오기")
    public ResponseEntity<PanelWatermarkInfoResponse> getPanelWatermarkInfo(@CurrentPanel Panel panel) {
        return ResponseEntity.ok(panelService.getPanelWatermarkInfo(panel));
    }

    @PatchMapping("")
    @Operation(summary = "App 마이페이지 패널 정보 수정하기")
    public ResponseEntity<PanelInfoVo> updatePanelInfo(
            @CurrentPanel Panel panel,
            @RequestBody PanelInfoUpdateDTO panelInfoUpdateDTO) {
        return ResponseEntity.ok(panelService.updatePanelInfo(panel, panelInfoUpdateDTO));
    }

    @GetMapping("/admin")
    @Operation(summary = "Admin 패널 리스트")
    public PanelAdminListResponse getAdminPanelList(@PageableDefault(size = 50) Pageable pageable,
                                                    @RequestParam(required = false) final String keyword) {
        return panelService.getAdminPanelList(pageable, keyword);
    }

    @GetMapping("/admin/csv")
    @Operation(summary = "Admin 패널 리스트 - csv 파일 추출용")
    public PanelAdminCsvListResponse getAdminPanelCsvList() {
        return panelService.getAdminPanelCsvList();
    }
}
