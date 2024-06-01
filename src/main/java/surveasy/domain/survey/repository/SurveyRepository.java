package surveasy.domain.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.survey.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long>, SurveyRepositoryCustom {

}
