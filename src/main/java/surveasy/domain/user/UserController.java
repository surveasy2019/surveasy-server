package surveasy.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.dto.request.RefreshTokenRequestDTO;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.dto.request.UserSignUpRequestDto;
import surveasy.domain.user.dto.response.TokenResponseDto;
import surveasy.domain.user.service.UserService;
import surveasy.global.common.annotation.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @Operation(summary = "고객 정보 1건 조회")
    @GetMapping
    public ResponseEntity<User> getUserInfo(@CurrentUser User user) {
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "고객 회원가입")
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserSignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "고객 로그인")
    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody UserSignInRequestDto signInRequestDto) {
        TokenResponseDto responseDto = userService.signIn(signInRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/reissue")
    @Operation(summary = "고객 엑세스 토큰 재발급 - 에러 해결 전")
    public ResponseEntity<TokenResponseDto> reissueAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        TokenResponseDto responseDto = userService.reissueToken(refreshTokenRequestDTO);
        return ResponseEntity.ok(responseDto);
    }

}
