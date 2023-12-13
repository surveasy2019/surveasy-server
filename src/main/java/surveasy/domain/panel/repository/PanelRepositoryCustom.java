package surveasy.domain.panel.repository;

import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.survey.domain.target.TargetGender;

import java.util.Date;
import java.util.Optional;

public interface PanelRepositoryCustom {

    long countActivePanel(TargetGender gender, Date aWeekAgo, Date birthFrom, Date birthTo);

    Optional<PanelInfoVo> findPanelInfoVo(Long panelId);
}
