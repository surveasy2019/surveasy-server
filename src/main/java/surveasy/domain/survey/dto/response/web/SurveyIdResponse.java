package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyIdResponse {

    private Long surveyId;

    @Builder
    public SurveyIdResponse(Long surveyId) {
        this.surveyId = surveyId;
    }

    public static SurveyIdResponse from(Long surveyId) {
        return SurveyIdResponse.builder()
                .surveyId(surveyId)
                .build();
    }
}
