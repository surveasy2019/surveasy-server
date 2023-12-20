package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.option.SurveySpendTime;

@Getter
public class SurveyAppListDetailVo {

    private final Long id;

    private final String title;

    private final Integer reward;

    private final String spendTime;

    private final Integer responseCount;

    private final String targetInput;

    private final String noticeToPanel;

    @Builder
    public SurveyAppListDetailVo(Long id, String title, Integer reward, SurveySpendTime spendTime,
                                 Integer responseCount, String targetInput, String noticeToPanel) {
        this.id = id;
        this.title = title;
        this.reward = reward;
        this.spendTime = spendTime.getValue();
        this.responseCount = responseCount;
        this.targetInput = targetInput;
        this.noticeToPanel = noticeToPanel;
    }
}
