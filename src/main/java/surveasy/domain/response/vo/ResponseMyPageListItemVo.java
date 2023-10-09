package surveasy.domain.response.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.domain.Response;
import surveasy.domain.survey.domain.Survey;
import surveasy.global.common.function.DateAndString;

@Getter
public class ResponseMyPageListItemVo {

    private Long responseId;

    private String title;

    private Integer reward;

    private String imgUrl;

    private String createdAt;

    @Builder
    private ResponseMyPageListItemVo(Long responseId, String title, Integer reward,
                                     String imgUrl, String createdAt) {
        this.responseId = responseId;
        this.title = title;
        this.reward = reward;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
    }

    public static ResponseMyPageListItemVo from(Response response) {
        Survey survey = response.getSurvey();

        return ResponseMyPageListItemVo.builder()
                .responseId(response.getId())
                .title(survey.getTitle())
                .reward(survey.getReward())
                .imgUrl(response.getImgUrl())
                .createdAt(DateAndString.dateToStringYMD(response.getCreatedAt()))
                .build();
    }
}
