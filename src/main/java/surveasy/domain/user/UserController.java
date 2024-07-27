package surveasy.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import surveasy.domain.user.dto.request.UserSignInRequestDto;
import surveasy.domain.user.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody UserSignInRequestDto signInRequestDto) {
        userService.signIn(signInRequestDto);
        return ResponseEntity.ok(null);
    }
}
