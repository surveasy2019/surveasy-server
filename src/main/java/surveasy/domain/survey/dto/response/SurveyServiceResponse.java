package surveasy.domain.survey.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyServiceResponse {

    private Long surveyId;

    @Builder
    public SurveyServiceResponse(Long surveyId) {
        this.surveyId = surveyId;
    }

    public static SurveyServiceResponse from(Long surveyId) {
        return SurveyServiceResponse.builder()
                .surveyId(surveyId)
                .build();
    }
}
