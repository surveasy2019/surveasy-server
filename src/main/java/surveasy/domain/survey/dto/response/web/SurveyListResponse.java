package surveasy.domain.survey.dto.response.web;


import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyListVo;

import java.util.List;

@Getter
public class SurveyListResponse {

    private List<SurveyListVo> surveyListVos;

    @Builder
    private SurveyListResponse(List<SurveyListVo> surveyListVos) {
        this.surveyListVos = surveyListVos;
    }

    public static SurveyListResponse from(List<SurveyListVo> surveyListVos) {
        return SurveyListResponse.builder()
                .surveyListVos(surveyListVos)
                .build();
    }

}
