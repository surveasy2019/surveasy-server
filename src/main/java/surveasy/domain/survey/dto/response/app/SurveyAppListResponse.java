package surveasy.domain.survey.dto.response.app;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.survey.vo.SurveyAppListVo;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class SurveyAppListResponse {

    private List<SurveyAppListVo> surveyAppList;
    private boolean didFirstSurvey;
    private PageInfo pageInfo;

    @Builder
    private SurveyAppListResponse(Page<SurveyAppListVo> surveyAppList, PanelStatus status) {
        this.surveyAppList = surveyAppList.getContent();

        if(status.equals(PanelStatus.FS_DONE))
            this.didFirstSurvey = true;
        else
            this.didFirstSurvey = false;

        this.pageInfo = PageInfo.from(surveyAppList);
    }

    public static SurveyAppListResponse of(Page<SurveyAppListVo> surveyAppListVos, PanelStatus status) {
        return SurveyAppListResponse.builder()
                .surveyAppList(surveyAppListVos)
                .status(status)
                .build();
    }
}
