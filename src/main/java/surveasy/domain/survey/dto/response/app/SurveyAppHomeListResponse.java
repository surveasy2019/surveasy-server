package surveasy.domain.survey.dto.response.app;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyAppHomeListItemVo;

import java.util.List;

@Getter
public class SurveyAppHomeListResponse {

    private List<SurveyAppHomeListItemVo> SurveyAppHomeListItemVos;

    @Builder
    private SurveyAppHomeListResponse(List<SurveyAppHomeListItemVo> SurveyAppHomeListItemVos) {
        this.SurveyAppHomeListItemVos = SurveyAppHomeListItemVos;
    }

    public static SurveyAppHomeListResponse from(List<SurveyAppHomeListItemVo> surveyAppHomeListItemVos) {
        return SurveyAppHomeListResponse.builder()
                .SurveyAppHomeListItemVos(surveyAppHomeListItemVos)
                .build();
    }
}
