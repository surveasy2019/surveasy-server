package surveasy.domain.survey.dto.response.web;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.survey.domain.Survey;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class SurveyAdminListResponse {

    private List<Survey> surveyList;
    private PageInfo pageInfo;

    @Builder
    private SurveyAdminListResponse(List<Survey> surveyList, PageInfo pageInfo) {
        this.surveyList = surveyList;
        this.pageInfo = pageInfo;
    }

    public static SurveyAdminListResponse of(Page<Survey> surveyList) {
        PageInfo pageInfo = PageInfo.from(surveyList);
        return SurveyAdminListResponse.builder()
                .surveyList(surveyList.getContent())
                .pageInfo(pageInfo)
                .build();
    }

}
