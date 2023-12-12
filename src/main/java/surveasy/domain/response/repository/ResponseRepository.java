package surveasy.domain.response.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long>, PagingAndSortingRepository<Response, Long>, ResponseRepositoryCustom {

    List<Response> findAllBySurveyId(Long surveyId);

    List<Response> findAllByPanelId(Long panelId);

    Long countByPanelIdAndStatus(Long panelId, ResponseStatus status);

}
