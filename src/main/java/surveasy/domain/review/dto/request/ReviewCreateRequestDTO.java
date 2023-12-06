package surveasy.domain.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewCreateRequestDTO {

    private Integer grade;
    private String content;
    private String username;
    private String email;
}
