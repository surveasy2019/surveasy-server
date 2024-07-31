package surveasy.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.dto.request.RefreshTokenRequestDTO;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.dto.request.UserSignUpRequestDto;
import surveasy.domain.user.dto.response.TokenResponseDto;
import surveasy.domain.user.exception.UserConflict;
import surveasy.domain.user.helper.UserHelper;
import surveasy.domain.user.mapper.UserMapper;
import surveasy.global.common.enm.AuthType;
import surveasy.global.security.jwt.TokenProvider;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final TokenProvider tokenProvider;

    @Override
    public void signUp(UserSignUpRequestDto signUpRequestDto) {
        validateExistingUser(signUpRequestDto);
        String encodedPassword = userHelper.encodePassword(signUpRequestDto.password());
        User newUser = User.createUser(signUpRequestDto, encodedPassword);
        userHelper.saveUser(newUser);
    }

    @Override
    public TokenResponseDto signIn(UserSignInRequestDto signInRequestDto) {
        User user = userHelper.addExistingUserIfNeed(signInRequestDto);
        final Authentication authentication = tokenProvider.userAuthorizationInput(user);
        final String accessToken = tokenProvider.createAccessToken(AuthType.USER, user.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(AuthType.USER, user.getId(), authentication);
        return userMapper.toTokenResponseDto(accessToken, refreshToken);
    }

    @Override
    public TokenResponseDto reissueToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        final String refreshToken = refreshTokenRequestDTO.getRefreshToken();
        final User user = userHelper.findUserByIdOrThrow(Long.parseLong(tokenProvider.getTokenId(refreshToken)));
        final Authentication authentication = tokenProvider.userAuthorizationInput(user);
        tokenProvider.validateRefreshToken(refreshToken);
        userHelper.matchesRefreshToken(refreshToken, user);
        final String newAccessToken = tokenProvider.createAccessToken(AuthType.USER, user.getId(), authentication);
        return userMapper.toTokenResponseDto(newAccessToken, refreshToken);
    }

    private void validateExistingUser(UserSignUpRequestDto signUpRequestDto) {
        User user = userHelper.findUserByEmailOrNull(signUpRequestDto.email());
        if(user != null) throw UserConflict.EXCEPTION;
    }
}
