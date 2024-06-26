package surveasy.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.request.ReviewUpdateRequestDTO;
import surveasy.domain.review.dto.response.AdminReviewListResponse;
import surveasy.domain.review.dto.response.HomeReviewVoListResponse;
import surveasy.domain.review.dto.response.ReviewIdResponse;
import surveasy.domain.review.helper.ReviewHelper;
import surveasy.domain.review.mapper.ReviewMapper;
import surveasy.domain.review.vo.ReviewVo;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewHelper reviewHelper;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewIdResponse createReview(Long surveyId, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return reviewMapper.toReviewIdResponse(reviewHelper.createReview(surveyId, reviewCreateRequestDTO));
    }

    public HomeReviewVoListResponse getHomeReviewVoList() {
        return reviewMapper.toHomeReviewVoListResponse(reviewHelper.getHomeReviewVoList());
    }

    public ReviewVo getReviewVoBySurveyId(Long surveyId) {
        return reviewHelper.getReviewVoBySurveyId(surveyId);
    }

    @Transactional
    public ReviewIdResponse updateReview(Long reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        return reviewMapper.toReviewIdResponse(reviewHelper.updateReview(reviewId, reviewUpdateRequestDTO));
    }

    public AdminReviewListResponse getAdminReviewList(Pageable pageable) {
        return reviewMapper.toAdminReviewListResponse(reviewHelper.getAdminReviewList(pageable));
    }
}
