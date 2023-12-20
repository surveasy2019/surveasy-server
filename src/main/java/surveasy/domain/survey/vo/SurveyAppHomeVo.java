package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyAppHomeVo {

    private Long id;

    private String title;

    private String targetInput;

    private String noticeToPanel;

    private Integer reward;

    private Integer responseCount;


    @Builder
    public SurveyAppHomeVo(Long id, String title, String targetInput, String noticeToPanel,
                           Integer reward, Integer responseCount) {
        this.id = id;
        this.title = title;
        this.targetInput = targetInput;
        this.noticeToPanel = noticeToPanel;
        this.reward = reward;
        this.responseCount = responseCount;
    }
}
