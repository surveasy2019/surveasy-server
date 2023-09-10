package surveasy.domain.survey.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import surveasy.domain.survey.domain.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long>, PagingAndSortingRepository<Survey, Long>, JpaSpecificationExecutor<Survey> {

    Long countByProgressGreaterThan(Integer min);

    Long countByEmailAndProgressEquals(String email, Integer progress);
    Long countByEmailAndProgressGreaterThanEqual(String email, Integer progress);

    Page<Survey> findAll(Pageable pageable);

    List<Survey> findAllByEmail(String email);

}
