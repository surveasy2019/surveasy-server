package surveasy.domain.user.service;

import surveasy.domain.panel.dto.request.RefreshTokenRequestDTO;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.dto.request.UserSignUpRequestDto;
import surveasy.domain.user.dto.response.TokenResponseDto;

public interface UserService {
    void signUp(UserSignUpRequestDto signUpRequestDto);
    TokenResponseDto signIn(UserSignInRequestDto signInRequestDto);
    TokenResponseDto reissueToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
