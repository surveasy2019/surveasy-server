package surveasy.domain.survey.dto.response.app;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.survey.vo.SurveyAppHomeVo;

import java.util.List;

@Getter
public class SurveyAppHomeListResponse {

    private List<SurveyAppHomeVo> surveyAppHomeList;
    private boolean didFirstSurvey;

    @Builder
    private SurveyAppHomeListResponse(List<SurveyAppHomeVo> surveyAppHomeList, PanelStatus status) {
        this.surveyAppHomeList = surveyAppHomeList;

        if(status.equals(PanelStatus.FS_ONLY_DONE) || status.equals(PanelStatus.FS_DONE))
            this.didFirstSurvey = true;
        else
            this.didFirstSurvey = false;
    }

    public static SurveyAppHomeListResponse of(List<SurveyAppHomeVo> surveyAppHomeVos, PanelStatus status) {
        return SurveyAppHomeListResponse.builder()
                .surveyAppHomeList(surveyAppHomeVos)
                .status(status)
                .build();
    }
}
