package surveasy.domain.review.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.review.domain.ReviewStatus;

@Getter
public class ReviewVo {

    private Long id;

    private String surveyTitle;

    private String username;

    private Integer grade;

    private String content;

    private ReviewStatus status;

    private String createdAt;

    @Builder
    public ReviewVo(Long id, String surveyTitle, String username, Integer grade, String content, ReviewStatus status, String createdAt) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.username = username;
        this.grade = grade;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
    }
}
