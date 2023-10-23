package surveasy.domain.activepanel.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.activepanel.domain.Activepanel;
import surveasy.domain.panel.domain.Panel;

import java.util.List;

@Component
public class ActivepanelMapper {

    public Activepanel toEntity(String totalList, String maleList, String femaleList) {
        return Activepanel.of(totalList, maleList, femaleList);
    }
}
