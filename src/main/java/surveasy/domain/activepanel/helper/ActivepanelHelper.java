package surveasy.domain.activepanel.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.activepanel.repository.ActivepanelRepository;

@RequiredArgsConstructor
@Component
public class ActivepanelHelper {
    private final ActivepanelRepository activepanelRepository;

    public Activepanel getActivePanel() {
        return activepanelRepository.findFirstByOrderByIdDesc();
    }
}
