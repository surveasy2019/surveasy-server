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
    public SurveyListItemVo(Long sid, String title, Integer progress, Date dueDate, String tarInput, Integer headCount, String username) {
        this.sid = sid;
        this.title = title;
        this.isDone = (progress > 2) ? true : false;
        this.dDay = isDone ? 0 : calculateDDay(dueDate);
        this.tarInput = tarInput;
        this.headCount = headCount;
        this.username = username;
    }

    private static Integer calculateDDay(Date dueDate) {
        Date now = new Date();
        Long diffSec = (dueDate.getTime() - now.getTime()) / 1000;
        Long diffDays = diffSec / (24 * 60 * 60);
        Integer dDay = diffDays.intValue();

        return dDay;
    }
}