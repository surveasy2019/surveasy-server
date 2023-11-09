package surveasy.domain.panel.repository;

import java.util.Date;

public interface PanelRepositoryCustom {

    long countActivePanel(Integer gender, Date aWeekAgo, Date birthFrom, Date birthTo);
}
