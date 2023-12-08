package surveasy.domain.review.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDTO {

    @NotNull
    private Integer grade;

    @NotNull
    private String content;

    @NotNull
    private String username;

    @NotNull
    private String email;
}
