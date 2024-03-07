package surveasy.domain.panel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AuthProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long>, PanelRepositoryCustom {

    Optional<Panel> findById(Long id);
    Optional<Panel> findByEmail(String email);

    Optional<Panel> findByEmailAndAuthProvider(String email, AuthProvider authProvider);

    Optional<Panel> findByAuthProviderAndAuthId(AuthProvider authProvider, String authId);

    Page<Panel> findAll(Pageable pageable);


}
