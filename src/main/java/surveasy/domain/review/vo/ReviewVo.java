package surveasy.domain.review.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewVo {

    private Long id;

    private String surveyTitle;

    private String username;

    private Integer grade;

    private String content;

    private String createdAt;

    @Builder
    public ReviewVo(Long id, String surveyTitle, String username, Integer grade, String content, String createdAt) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.username = username;
        this.grade = grade;
        this.content = content;
        this.createdAt = createdAt;
    }
}
