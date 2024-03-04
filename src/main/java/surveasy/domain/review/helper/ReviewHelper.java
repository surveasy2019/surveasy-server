package surveasy.domain.review.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import surveasy.domain.review.domain.Review;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.request.ReviewUpdateRequestDTO;
import surveasy.domain.review.exception.ReviewDuplicateData;
import surveasy.domain.review.exception.ReviewNotAllowed;
import surveasy.domain.review.exception.ReviewNotFound;
import surveasy.domain.review.mapper.ReviewMapper;
import surveasy.domain.review.repository.ReviewRepository;
import surveasy.domain.review.vo.ReviewVo;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.repository.SurveyRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewHelper {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final SurveyRepository surveyRepository;
    private final SurveyHelper surveyHelper;

    public Long createReview(Long surveyId, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        // 이미 작성한 리뷰 존재 여부
        if(survey.getReviewId() != null) {
            throw ReviewDuplicateData.EXCEPTION;
        }

        // 설문 종료 여부
        if(!surveyHelper.isDone(survey.getStatus())) {
            throw ReviewNotAllowed.EXCEPTION;
        }

        Review newReview = reviewMapper.toEntity(survey, reviewCreateRequestDTO);
        Review savedReview = reviewRepository.save(newReview);
        survey.setReviewId(savedReview.getId());

        return savedReview.getId();
    }

    public List<ReviewVo> getHomeReviewVoList() {
        return reviewRepository.findAllHomeReviewVo();
    }

    public ReviewVo getReviewVoBySurveyId(Long surveyId) {
        return reviewRepository.findReviewVoBySurveyId(surveyId)
                .orElseThrow(() -> ReviewNotFound.EXCEPTION);
    }

    public Long updateReview(Long reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewNotFound.EXCEPTION);

        if(reviewUpdateRequestDTO.getGrade() != null) {
            review.setGrade(reviewUpdateRequestDTO.getGrade());
        }

        if(reviewUpdateRequestDTO.getContent() != null) {
            review.setContent(reviewUpdateRequestDTO.getContent());
        }

        if(reviewUpdateRequestDTO.getStatus() != null) {
            review.setStatus(reviewUpdateRequestDTO.getStatus());
        }

        return review.getId();
    }

    public Page<ReviewVo> getAdminReviewList(Pageable pageable) {
        return reviewRepository.findAllByOrderById(pageable);
    }
}
