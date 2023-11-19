package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class SurveyListItemVo {

    private Long sid;

    private String title;

    private Boolean isDone;

    private Integer dDay = 0;

    private Integer dTime = 0;

    private String tarInput;

    private Integer headCount;

    private String username;

    @Builder
    public SurveyListItemVo(Long sid, String title, Integer progress, Date dueDate, String tarInput, Integer headCount, String username) {
        this.sid = sid;
        this.title = title;
        this.isDone = (progress > 2) ? true : false;
        this.tarInput = tarInput;
        this.headCount = headCount;
        this.username = username;

        if(progress == 2) {
            int dday = calculateDDay(dueDate);

            if(dday > 0) {
                this.dDay = dday;
            }

            else {
                this.dTime = calculateDTime(dueDate);
            }
        }
    }

    private static Integer calculateDDay(Date dueDate) {
        Date now = new Date();
        Long diffSec = (dueDate.getTime() - now.getTime()) / 1000;
        Long diffDays = diffSec / (24 * 60 * 60);
        Integer dDay = diffDays.intValue();

        return dDay;
    }

    private static Integer calculateDTime(Date dueDate) {
        Date now = new Date();
        Long diffSec = (dueDate.getTime() - now.getTime()) / 1000;
        Long diffHours = diffSec / (60 * 60);
        Integer dHour = diffHours.intValue();

        return dHour;
    }
}