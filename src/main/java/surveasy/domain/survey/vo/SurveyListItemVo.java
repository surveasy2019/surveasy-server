package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

@Getter
public class SurveyListItemVo {

    private Long lastIdChecked;

    private String title;

    private Boolean isDone;

    private Integer dDay;

    private String target;

    private Integer requiredHeadCount;

    private String username;

    @Builder
    public SurveyListItemVo(Long lastIdChecked, String title, Boolean isDone, Integer dDay, String target, Integer requiredHeadCount, String username) {
        this.lastIdChecked = lastIdChecked;
        this.title = title;
        this.isDone = isDone;
        this.dDay = dDay;
        this.target = target;
        this.requiredHeadCount = requiredHeadCount;
        this.username = username;
    }

    public static SurveyListItemVo from(Survey survey) {
        return SurveyListItemVo.builder()
                .lastIdChecked(survey.getLastCheckedId())
                .title(survey.getTitle())
                .isDone(false)
                .dDay(7)
                .target(survey.getTarget())
                .requiredHeadCount(survey.getRequiredHeadCount())
                .username(survey.getUploader())
                .build();
    }
}