package surveasy.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewIdResponse {

    private Long reviewId;

    @Builder
    private ReviewIdResponse(Long reviewId) {
        this.reviewId = reviewId;
    }

    public static ReviewIdResponse from(Long reviewId) {
        return ReviewIdResponse.builder()
                .reviewId(reviewId)
                .build();
    }
}
