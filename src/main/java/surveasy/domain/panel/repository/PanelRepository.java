package surveasy.domain.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.panel.domain.Panel;

import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long> {

    Optional<Panel> findById(Long id);
    Optional<Panel> findByUid(String uid);

}
