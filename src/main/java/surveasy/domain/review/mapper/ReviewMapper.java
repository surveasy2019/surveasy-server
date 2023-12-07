package surveasy.domain.review.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.review.domain.Review;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.response.HomeReviewVoListResponse;
import surveasy.domain.review.dto.response.ReviewIdResponse;
import surveasy.domain.review.vo.HomeReviewVo;
import surveasy.domain.survey.domain.Survey;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public Review toEntity(Survey survey, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return Review.of(survey, reviewCreateRequestDTO);
    }

    public ReviewIdResponse toReviewIdResponse(Long reviewId) {
        return ReviewIdResponse.from(reviewId);
    }

    public HomeReviewVoListResponse toHomeReviewVoListResponse(List<HomeReviewVo> homeReviewVoList) {
        return HomeReviewVoListResponse.from(homeReviewVoList);
    }
}
