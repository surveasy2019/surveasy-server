package surveasy.domain.activepanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.activepanel.domain.Activepanel;

public interface ActivepanelRepository extends JpaRepository<Activepanel, Long> {

    Activepanel findFirstByOrderByIdDesc();
}
