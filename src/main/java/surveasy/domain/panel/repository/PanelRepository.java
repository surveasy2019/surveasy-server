package surveasy.domain.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AuthProvider;

import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long>, PanelRepositoryCustom {

    Optional<Panel> findById(Long id);
    Optional<Panel> findByEmail(String email);

    Optional<Panel> findByEmailAndAuthProvider(String email, AuthProvider authProvider);

    Optional<Panel> findByAuthProviderAndAuthId(AuthProvider authProvider, String authId);

}
