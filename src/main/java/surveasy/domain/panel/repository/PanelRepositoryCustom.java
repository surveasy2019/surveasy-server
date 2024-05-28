package surveasy.domain.panel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.vo.PanelAdminCsvVo;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PanelRepositoryCustom {

    long countActivePanel(TargetGender gender, LocalDate aWeekAgo, LocalDate birthFrom, LocalDate birthTo);

    Optional<PanelInfoVo> findPanelInfoVo(Long panelId);

    List<PanelAdminCsvVo> findAllPanelAdminCsvVos();

    Page<Panel> findAll(Pageable pageable, String keyword);
}
