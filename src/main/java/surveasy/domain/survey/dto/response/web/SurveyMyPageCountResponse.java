package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyMyPageCountResponse {

    private Long surveyOngoing;
    private Long surveyDone;

    @Builder
    private SurveyMyPageCountResponse(Long surveyOngoing, Long surveyDone) {
        this.surveyOngoing = surveyOngoing;
        this.surveyDone = surveyDone;
    }

    public static SurveyMyPageCountResponse from(Long surveyOngoing, Long surveyDone) {
        return SurveyMyPageCountResponse.builder()
                .surveyOngoing(surveyOngoing)
                .surveyDone(surveyDone)
                .build();
    }
}
