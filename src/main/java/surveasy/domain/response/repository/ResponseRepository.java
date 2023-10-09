package surveasy.domain.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.response.domain.Response;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long>, ResponseRepositoryCustom {

    List<Response> findAllBySurveyId(Long surveyId);

    List<Response> findAllByPanelId(Long panelId);

}
