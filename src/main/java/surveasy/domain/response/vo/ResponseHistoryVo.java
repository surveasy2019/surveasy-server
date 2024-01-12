package surveasy.domain.response.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.global.common.util.DateAndStringUtils;

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

    @Builder
    public ResponseHistoryVo(Long id, String surveyTitle, Integer surveyReward, String imgUrl,
                             LocalDateTime createdAt, LocalDate sentAt) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.surveyReward = surveyReward;
        this.imgUrl = imgUrl;
        this.createdAt = DateAndStringUtils.localDateTimeToString(createdAt);
        if(sentAt != null) this.sentAt = DateAndStringUtils.localDateToString(sentAt);
    }
}
