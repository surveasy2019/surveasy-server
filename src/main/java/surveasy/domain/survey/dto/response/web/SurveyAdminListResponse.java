package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class SurveyAdminListResponse {

    private List<Survey> surveyList;
    private PageInfo pageInfo;

    @Builder
    private SurveyAdminListResponse(List<Survey> surveyList, PageInfo pageInfo) {
        this.surveyList = surveyList;
        this.pageInfo = pageInfo;
    }

    public static SurveyAdminListResponse of(List<Survey> surveyList, PageInfo pageInfo) {
        return SurveyAdminListResponse.builder()
                .pageInfo(pageInfo)
                .surveyList(surveyList)
                .build();
    }

}
