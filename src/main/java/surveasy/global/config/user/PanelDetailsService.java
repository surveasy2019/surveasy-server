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
public class PanelDetailsService implements UserDetailsService {

    private final PanelRepository panelRepository;


    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        return panelRepository.findById(id)
                .map(panel -> createPanel(id, panel))
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> DB에서 찾을 수 없음"));
    }


    private UserDetails createPanel(Long id, Panel panel) {
        return new PanelDetails(panel);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
