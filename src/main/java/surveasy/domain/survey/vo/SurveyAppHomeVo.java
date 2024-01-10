package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyAppHomeVo {

    private Long id;

    private String title;

    private String targetInput;

    private Integer reward;

    private Integer responseCount;


    @Builder
    public SurveyAppHomeVo(Long id, String title, String targetInput,
                           Integer reward, Integer responseCount) {
        this.id = id;
        this.title = title;
        this.targetInput = targetInput;
        this.reward = reward;
        this.responseCount = responseCount;
    }
}
