package surveasy.domain.user.service;

import surveasy.domain.user.domain.User;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.dto.response.TokenResponseDto;

public interface UserService {
    TokenResponseDto signIn(UserSignInRequestDto signInRequestDto);

    User findUserById(Long id);
}
