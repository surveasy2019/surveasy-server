package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SurveyHomeResponse {

    private Long surveyCount;
    private Long panelCount;

    @Builder
    public SurveyHomeResponse(Long surveyCount, Long panelCount) {
        this.surveyCount = surveyCount;
        this.panelCount = panelCount;
    }

    public static SurveyHomeResponse from(Long surveyCount, Long panelCount) {
        return SurveyHomeResponse.builder()
                .surveyCount(surveyCount)
                .panelCount(panelCount)
                .build();
    }

    // review list
}
