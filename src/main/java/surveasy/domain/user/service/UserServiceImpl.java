package surveasy.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.dto.response.TokenResponseDto;
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
    public TokenResponseDto signIn(UserSignInRequestDto signInRequestDto) {
        User user = userHelper.addExistingUserIfNeed(signInRequestDto);
        final Authentication authentication = tokenProvider.userAuthorizationInput(user);
        final String accessToken = tokenProvider.createAccessToken(AuthType.USER, user.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(AuthType.USER, user.getId(), authentication);
        return userMapper.toTokenResponseDto(accessToken, refreshToken);
    }

    @Override
    public User findUserById(Long id) {
        return userHelper.findUserByIdOrThrow(id);
    }
}
