package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyMyPageOrderListItemVo;

import java.util.List;

@Getter
public class SurveyMyPageOrderListResponse {

    private List<SurveyMyPageOrderListItemVo> surveyMyPageOrderList;

    @Builder
    private SurveyMyPageOrderListResponse(List<SurveyMyPageOrderListItemVo> surveyMyPageOrderListItemVos) {
        this.surveyMyPageOrderList = surveyMyPageOrderListItemVos;
    }

    public static SurveyMyPageOrderListResponse from(List<SurveyMyPageOrderListItemVo> surveyMyPageOrderListItemVos) {
        return SurveyMyPageOrderListResponse.builder()
                .surveyMyPageOrderListItemVos(surveyMyPageOrderListItemVos)
                .build();
    }
}
