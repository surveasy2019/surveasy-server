package surveasy.domain.survey.dto.response.web;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class SurveyListResponse {

    private List<SurveyListVo> surveyListVos;
    private PageInfo pageInfo;

    @Builder
    private SurveyListResponse(Page<SurveyListVo> surveyListVos) {
        this.surveyListVos = surveyListVos.getContent();
        this.pageInfo = PageInfo.from(surveyListVos);
    }

    public static SurveyListResponse from(Page<SurveyListVo> surveyListVos) {
        return SurveyListResponse.builder()
                .surveyListVos(surveyListVos)
                .build();
    }

}
