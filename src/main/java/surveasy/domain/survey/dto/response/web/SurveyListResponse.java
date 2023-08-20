package surveasy.domain.survey.dto.response.web;


import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyListItemVo;

import java.util.List;

@Getter
public class SurveyListResponse {

    private List<SurveyListItemVo> surveyListItemVos;

    @Builder
    private SurveyListResponse(List<SurveyListItemVo> surveyListItemVos) {
        this.surveyListItemVos = surveyListItemVos;
    }

    public static SurveyListResponse from(List<SurveyListItemVo> surveyListItemVos) {
        return SurveyListResponse.builder()
                .surveyListItemVos(surveyListItemVos)
                .build();
    }

}
