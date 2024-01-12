package surveasy.domain.panel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.dto.request.PanelInfoUpdateDTO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelExistingDTO;
import surveasy.domain.panel.dto.request.RefreshTokenRequestDTO;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.exception.Oauth2DuplicateUser;
import surveasy.domain.panel.service.PanelService;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.config.user.PanelDetails;
import surveasy.global.error.BaseErrorException;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/panel")
@RequiredArgsConstructor
@Tag(name = "Panel")
public class PanelController {

    private final PanelService panelService;
    private final ObjectMapper objectMapper;

    @GetMapping("/oauth2")
    public ResponseEntity<?> oauth2(@RequestParam(name = "result", required = false) String result,
                                    @RequestParam(name = "error", required = false) String error) throws IOException {
        if(result != null) return ResponseEntity.ok(objectMapper.readValue(result, PanelTokenResponse.class));
        else return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup/existing")
    @Operation(summary = "App 기존 패널 회원 가입")
    public PanelTokenResponse signUpExisting(@RequestBody @Valid PanelExistingDTO panelExistingDTO) throws ExecutionException, InterruptedException, ParseException {
        return panelService.signUpExisting(panelExistingDTO);
    }

    @PostMapping("/signup")
    @Operation(summary = "App 신규 패널 추가 정보 입력")
    public PanelIdResponse signUp(@AuthenticationPrincipal PanelDetails panelDetails,
                                  @RequestBody @Valid PanelSignUpDTO panelSignUpDTO) {
        return panelService.signUp(panelDetails, panelSignUpDTO);
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
