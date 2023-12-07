package surveasy.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.review.vo.HomeReviewVo;

import java.util.List;

@Getter
public class HomeReviewVoListResponse {

    private List<HomeReviewVo> homeReviewList;

    @Builder
    private HomeReviewVoListResponse(List<HomeReviewVo> homeReviewList) {
        this.homeReviewList = homeReviewList;
    }

    public static HomeReviewVoListResponse from(List<HomeReviewVo> homeReviewList) {
        return HomeReviewVoListResponse.builder()
                .homeReviewList(homeReviewList)
                .build();
    }
}
