package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyHomeResponse {

    private Long surveyCount;

    @Builder
    public SurveyHomeResponse(Long surveyCount) {
        this.surveyCount = surveyCount;
    }

    public static SurveyHomeResponse from(Long surveyCount) {
        return SurveyHomeResponse.builder()
                .surveyCount(surveyCount)
                .build();
    }

    // review list
}
