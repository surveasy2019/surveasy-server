package surveasy.global.config.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.repository.PanelRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PanelRepository panelRepository;


    @Transactional
    public UserDetails loadUserByUid(String uid) throws UsernameNotFoundException {
        return panelRepository.findByUid(uid)
                .map(panel -> createPanel(uid, panel))
                .orElseThrow(() -> new UsernameNotFoundException(uid + " -> DB에서 찾을 수 없음"));
    }


    private UserDetails createPanel(String uid, Panel panel) {
        return new UserDetailsImpl(panel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
