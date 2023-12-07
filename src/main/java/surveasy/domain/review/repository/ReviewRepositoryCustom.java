package surveasy.domain.review.repository;

import surveasy.domain.review.vo.ReviewVo;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    List<ReviewVo> findAllHomeReviewVo();

    Optional<ReviewVo> findReviewVoBySurveyId(Long surveyId);
}
