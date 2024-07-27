package surveasy.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.helper.UserHelper;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserHelper userHelper;

    @Override
    public void signIn(UserSignInRequestDto signInRequestDto) {
        User user = userHelper.addExistingUserIfNeed(signInRequestDto);

    }
}
