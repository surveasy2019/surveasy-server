package surveasy.domain.survey.dto.response.app;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyAppListVo;

import java.util.List;

@Getter
public class SurveyAppListResponse {

    private List<SurveyAppListVo> surveyAppList;

    @Builder
    private SurveyAppListResponse(List<SurveyAppListVo> surveyAppList) {
        this.surveyAppList = surveyAppList;
    }

    public static SurveyAppListResponse from(List<SurveyAppListVo> surveyAppListVos) {
        return SurveyAppListResponse.builder()
                .surveyAppList(surveyAppListVos)
                .build();
    }
}
