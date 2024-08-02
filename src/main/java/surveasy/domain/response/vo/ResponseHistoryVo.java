package surveasy.domain.response.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.global.common.util.string.DateAndStringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResponseHistoryVo {

    private Long id;

    private String surveyTitle;

    private Integer surveyReward;

    private String imgUrl;

    private String createdAt;

    private String sentAt;

    private ResponseStatus responseStatus;
    private SurveyStatus surveyStatus;

    @Builder
    public ResponseHistoryVo(Long id, String surveyTitle, Integer surveyReward, String imgUrl,
                             LocalDateTime createdAt, LocalDate sentAt, ResponseStatus responseStatus, SurveyStatus surveyStatus) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.surveyReward = surveyReward;
        this.imgUrl = imgUrl;
        this.createdAt = DateAndStringUtils.localDateTimeToString(createdAt);
        if(sentAt != null) this.sentAt = DateAndStringUtils.localDateToString(sentAt);
        this.responseStatus = responseStatus;
        this.surveyStatus = surveyStatus;
    }
}
