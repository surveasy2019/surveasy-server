package surveasy.domain.response.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import surveasy.domain.response.vo.ResponseHistoryVo;

public interface ResponseRepositoryCustom {

    Page<ResponseHistoryVo> findByPanelIdAndStatusType(Long panelId, String statusType, Pageable pageable);
}
