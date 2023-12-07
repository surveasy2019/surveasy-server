package surveasy.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.review.vo.ReviewVo;

import java.util.List;

@Getter
public class HomeReviewVoListResponse {

    private List<ReviewVo> homeReviewList;

    @Builder
    private HomeReviewVoListResponse(List<ReviewVo> homeReviewList) {
        this.homeReviewList = homeReviewList;
    }

    public static HomeReviewVoListResponse from(List<ReviewVo> homeReviewList) {
        return HomeReviewVoListResponse.builder()
                .homeReviewList(homeReviewList)
                .build();
    }
}
