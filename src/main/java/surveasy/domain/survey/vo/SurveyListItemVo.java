package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

@Getter
public class SurveyListItemVo {

    private Long sid;

    private String title;

    private Boolean isDone;

    private Integer dDay;

    private String tarInput;

    private Integer headCount;

    private String username;

    @Builder
    public SurveyListItemVo(Long sid, String title, Boolean isDone, Integer dDay, String tarInput, Integer headCount, String username) {
        this.sid = sid;
        this.title = title;
        this.isDone = isDone;
        this.dDay = dDay;
        this.tarInput = tarInput;
        this.headCount = headCount;
        this.username = username;
    }

    public static SurveyListItemVo from(Survey survey) {
        return SurveyListItemVo.builder()
                .sid(survey.getSid())
                .title(survey.getTitle())
                .isDone(false)
                .dDay(7)
                .tarInput(survey.getTarInput())
                .headCount(survey.getHeadCount())
                .username(survey.getUsername())
                .build();
    }
}