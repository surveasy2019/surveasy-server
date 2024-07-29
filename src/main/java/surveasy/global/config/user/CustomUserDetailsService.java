package surveasy.global.config.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import surveasy.domain.user.domain.User;
import surveasy.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUserId(Long id) {
        return userRepository.findById(id)
                .map(user -> createUser(user))
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> DB에서 찾을 수 없음"));
    }

    private UserDetails createUser(User user) {
        return new CustomUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
