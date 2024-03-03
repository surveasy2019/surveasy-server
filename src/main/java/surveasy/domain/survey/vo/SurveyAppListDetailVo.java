package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveySpendTime;

@Getter
public class SurveyAppListDetailVo {

    private final Long id;

    private final String title;

    private final Integer reward;

    private final Integer headCount;

    private final String spendTime;

    private final Integer responseCount;

    private final String targetInput;

    private final String noticeToPanel;

    private final String link;

    private final String description;

    @Builder
    public SurveyAppListDetailVo(Long id, String title, Integer reward, SurveyHeadcount headCount, SurveySpendTime spendTime,
                                 Integer responseCount, String targetInput, String noticeToPanel, String link, String description) {
        this.id = id;
        this.title = title;
        this.reward = reward;
        this.headCount = headCount.getValue();
        this.spendTime = spendTime.getValue();
        this.responseCount = responseCount;
        this.targetInput = targetInput;
        this.noticeToPanel = noticeToPanel;
        this.link = link;
        this.description = description;
    }
}
