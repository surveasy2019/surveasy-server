package surveasy.domain.activepanel.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.repository.ActivepanelRepository;

@Component
@RequiredArgsConstructor
public class ActivepanelHelper {

    private final ActivepanelRepository activepanelRepository;

    // id max인 ActivePanelEntity response
}
