package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;

import java.util.List;

@Getter
public class SurveyMyPageOrderListResponse {

    private List<SurveyMyPageOrderVo> surveyMyPageOrderList;

    @Builder
    private SurveyMyPageOrderListResponse(List<SurveyMyPageOrderVo> surveyMyPageOrderList) {
        this.surveyMyPageOrderList = surveyMyPageOrderList;
    }

    public static SurveyMyPageOrderListResponse from(List<SurveyMyPageOrderVo> surveyMyPageOrderVos) {
        return SurveyMyPageOrderListResponse.builder()
                .surveyMyPageOrderList(surveyMyPageOrderVos)
                .build();
    }
}
