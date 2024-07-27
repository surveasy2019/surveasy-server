package surveasy.domain.user.service;

import surveasy.domain.user.dto.request.UserSignInRequestDto;

public interface UserService {
    void signIn(UserSignInRequestDto signInRequestDto);
}
