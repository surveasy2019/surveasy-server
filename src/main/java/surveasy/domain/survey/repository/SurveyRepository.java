package surveasy.domain.survey.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;

public interface SurveyRepository extends JpaRepository<Survey, Long>, PagingAndSortingRepository<Survey, Long>, JpaSpecificationExecutor<Survey>, SurveyRepositoryCustom {
    Page<Survey> findAll(Pageable pageable);

}
