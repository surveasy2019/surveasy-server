package surveasy.domain.survey.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyListItemVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderListItemVo;

import java.util.Comparator;
import java.util.List;

// Mapper의 역할 1 : Entity로 매핑
// Mapper의 역할 2 : DTO로 매핑

@Component
public class SurveyMapper {

    public Survey toEntity(SurveyServiceDTO surveyServiceDTO) {
        return Survey.of(surveyServiceDTO);
    }

    public SurveyIdResponse toSurveyIdResponse(Long surveyId) {
        return SurveyIdResponse.from(surveyId);
    }

    public SurveyListResponse toSurveyListResponse(List<Survey> surveyList) {
        List<SurveyListItemVo> surveyListItemVos = surveyList.stream()
                .filter(survey -> survey.getProgress() >= 2)
                .sorted(Comparator.comparing(Survey::getId).reversed())
                .map(SurveyListItemVo::from)
                .toList();

        return SurveyListResponse.from(surveyListItemVos);
    }

    public SurveyHomeResponse toSurveyHomeResponse(Long surveyCount) {
        return SurveyHomeResponse.from(surveyCount);
    }

    public SurveyMyPageCountResponse toSurveyMyPageCountResponse(Long surveyOngoing, Long surveyDone) {
        return SurveyMyPageCountResponse.from(surveyOngoing, surveyDone);
    }

    public SurveyMyPageOrderListResponse toSurveyMyPageOrderListResponse(List<Survey> surveyList) {
        List<SurveyMyPageOrderListItemVo> surveyMyPageOrderListItemVos = surveyList.stream()
                .sorted(Comparator.comparing(Survey::getId).reversed())
                .map(SurveyMyPageOrderListItemVo::from)
                .toList();

        return SurveyMyPageOrderListResponse.from(surveyMyPageOrderListItemVos);
    }
}
