package surveasy.domain.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.panel.domain.Panel;

public interface PanelRepository extends JpaRepository<Panel, Long> {
}
