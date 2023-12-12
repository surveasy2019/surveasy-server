package surveasy.domain.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long>, PagingAndSortingRepository<Response, Long>, ResponseRepositoryCustom {

    Optional<Response> findByPanelIdAndSurveyId(Long panelId, Long surveyId);

    long countByPanelIdAndStatusNot(Long panelId, ResponseStatus status);

}
