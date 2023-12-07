package surveasy.domain.review.dto.request;

import lombok.Getter;
import surveasy.domain.review.domain.ReviewStatus;

@Getter
public class ReviewUpdateRequestDTO {

    private Integer grade;
    private String content;
    private ReviewStatus status;

}
