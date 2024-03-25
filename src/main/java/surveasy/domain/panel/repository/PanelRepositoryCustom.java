package surveasy.domain.panel.repository;

import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.Optional;

public interface PanelRepositoryCustom {

    long countActivePanel(TargetGender gender, LocalDate aWeekAgo, LocalDate birthFrom, LocalDate birthTo);

    Optional<PanelInfoVo> findPanelInfoVo(Long panelId);
}
