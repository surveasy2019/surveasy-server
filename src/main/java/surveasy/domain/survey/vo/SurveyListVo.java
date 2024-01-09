package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.option.SurveyHeadcount;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class SurveyListVo {

    private final Long sid;

    private final String title;

    private final String link;

    private final Boolean isDone;

    private int dDay = 0;

    private int dTime = 0;

    private final String tarInput;

    private final int headCount;

    private final Integer responseCount;

    private final String username;

    @Builder
    public SurveyListVo(Long sid, String title, String link, SurveyStatus status, LocalDateTime dueDate,
                        String tarInput, SurveyHeadcount headCount, Integer responseCount, String username) {
        this.sid = sid;
        this.title = title;
        this.link = link;
        this.isDone = status.equals(SurveyStatus.DONE);
        this.tarInput = tarInput;
        this.headCount = headCount.getValue();
        this.responseCount = responseCount;
        this.username = username;

        if(status.equals(SurveyStatus.IN_PROGRESS)) {
            int dday = calculateDDay(dueDate);

            if(dday > 0) {
                this.dDay = dday;
            }

            else {
                this.dTime = calculateDTime(dueDate);
            }
        }
    }

    private static int calculateDDay(LocalDateTime dueDate) {
        LocalDateTime now = LocalDateTime.now();
        long diffDays = ChronoUnit.DAYS.between(now, dueDate);
        return (int) diffDays;
    }

    private static int calculateDTime(LocalDateTime dueDate) {
        LocalDateTime now = LocalDateTime.now();
        long diffHours = ChronoUnit.HOURS.between(now, dueDate);
        return (int) diffHours;
    }
}