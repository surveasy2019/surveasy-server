package surveasy.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.response.ReviewIdResponse;
import surveasy.domain.review.helper.ReviewHelper;
import surveasy.domain.review.mapper.ReviewMapper;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewHelper reviewHelper;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewIdResponse createReview(Long surveyId, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return reviewMapper.toReviewIdResponse(reviewHelper.createReview(surveyId, reviewCreateRequestDTO));
    }
}
