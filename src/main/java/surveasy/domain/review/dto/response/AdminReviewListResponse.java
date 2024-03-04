package surveasy.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.review.vo.ReviewVo;
import surveasy.global.common.dto.PageInfo;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AdminReviewListResponse {

    private List<ReviewVo> reviewList;
    private PageInfo pageInfo;

    @Builder
    private AdminReviewListResponse(Page<ReviewVo> reviewList) {
        this.reviewList = reviewList.hasContent() ? reviewList.getContent() : new ArrayList<>();
        this.pageInfo = PageInfo.from(reviewList);
    }

    public static AdminReviewListResponse from(Page<ReviewVo> reviewList) {
        return AdminReviewListResponse.builder()
                .reviewList(reviewList)
                .build();
    }
}
