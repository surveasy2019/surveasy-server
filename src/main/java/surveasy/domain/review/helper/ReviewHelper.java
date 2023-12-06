package surveasy.domain.review.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.review.domain.Review;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.mapper.ReviewMapper;
import surveasy.domain.review.repository.ReviewRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.repository.SurveyRepository;

@Component
@RequiredArgsConstructor
public class ReviewHelper {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final SurveyRepository surveyRepository;


    public Long createReview(Long surveyId, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });

        Review newReview = reviewMapper.toEntity(survey, reviewCreateRequestDTO);
        Review savedReview = reviewRepository.save(newReview);
        return savedReview.getId();
    }

}
