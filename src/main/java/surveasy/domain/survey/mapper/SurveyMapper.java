package surveasy.domain.survey.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppHomeListItemVo;
import surveasy.domain.survey.vo.SurveyListItemVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderListItemVo;

import java.util.Comparator;
import java.util.List;

// Mapper의 역할 1 : Entity로 매핑
// Mapper의 역할 2 : DTO로 매핑

@Component
@RequiredArgsConstructor
public class SurveyMapper {

    private final ResponseRepository responseRepository;

    public Survey toEntity(SurveyServiceDTO surveyServiceDTO) {
        return Survey.of(surveyServiceDTO);
    }

    public SurveyIdResponse toSurveyIdResponse(Long surveyId) {
        return SurveyIdResponse.from(surveyId);
    }

    public SurveyListResponse toSurveyListResponse(List<SurveyListItemVo> surveyListItemVos) {
        return SurveyListResponse.from(surveyListItemVos);
    }

    public SurveyHomeResponse toSurveyHomeResponse(Long surveyCount, Long panelCount) {
        return SurveyHomeResponse.from(surveyCount, panelCount);
    }

    public SurveyMyPageCountResponse toSurveyMyPageCountResponse(Long surveyOngoing, Long surveyDone) {
        return SurveyMyPageCountResponse.from(surveyOngoing, surveyDone);
    }

    public SurveyMyPageOrderListResponse toSurveyMyPageOrderListResponse(List<SurveyMyPageOrderListItemVo> surveyMyPageOrderListItemVos) {
        return SurveyMyPageOrderListResponse.from(surveyMyPageOrderListItemVos);
    }

    public SurveyAppHomeListResponse toSurveyAppHomeListResponse(Long panelId, List<SurveyAppHomeListItemVo> surveyAppHomeListItemVos) {
        surveyAppHomeListItemVos = surveyAppHomeListItemVos.stream()
                .filter(survey -> responseRepository.findByPidAndSidAndIsValid(panelId, survey.getId(), true) == null)
                .limit(3)
                .toList();
        return SurveyAppHomeListResponse.from(surveyAppHomeListItemVos);
    }
}
