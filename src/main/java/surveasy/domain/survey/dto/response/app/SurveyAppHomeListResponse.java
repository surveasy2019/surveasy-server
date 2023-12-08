package surveasy.domain.survey.dto.response.app;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.vo.SurveyAppHomeVo;

import java.util.List;

@Getter
public class SurveyAppHomeListResponse {

    private List<SurveyAppHomeVo> surveyAppHomeList;

    @Builder
    private SurveyAppHomeListResponse(List<SurveyAppHomeVo> surveyAppHomeList) {
        this.surveyAppHomeList = surveyAppHomeList;
    }

    public static SurveyAppHomeListResponse from(List<SurveyAppHomeVo> surveyAppHomeVos) {
        return SurveyAppHomeListResponse.builder()
                .surveyAppHomeList(surveyAppHomeVos)
                .build();
    }
}
