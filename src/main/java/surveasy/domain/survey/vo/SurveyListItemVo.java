package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

import java.util.Date;

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
        Boolean isDone = (survey.getProgress() > 2) ? true : false;
        Integer dDay = isDone ? 0 : calculateDDay(survey.getDueDate());

        return SurveyListItemVo.builder()
                .sid(survey.getSid())
                .title(survey.getTitle())
                .isDone(isDone)
                .dDay(dDay)
                .tarInput(survey.getTarInput())
                .headCount(survey.getHeadCount())
                .username(survey.getUsername())
                .build();
    }

    private static Integer calculateDDay(Date dueDate) {
        Date now = new Date();
        Long diffSec = (dueDate.getTime() - now.getTime()) / 1000;
        Long diffDays = diffSec / (24 * 60 * 60);
        Integer dDay = diffDays.intValue();

        return dDay;
    }
}