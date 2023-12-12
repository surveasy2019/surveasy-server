package surveasy.domain.survey.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppHomeVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;

import java.util.List;

// Mapper의 역할 1 : Entity로 매핑
// Mapper의 역할 2 : DTO로 매핑

@Component
@RequiredArgsConstructor
public class SurveyMapper {

    private final ResponseRepository responseRepository;

    public Survey toEntity(SurveyServiceDTO surveyServiceDTO) {
        return Survey.from(surveyServiceDTO);
    }

    public SurveyIdResponse toSurveyIdResponse(Long surveyId) {
        return SurveyIdResponse.from(surveyId);
    }

    public SurveyListResponse toSurveyListResponse(List<SurveyListVo> surveyListVos) {
        return SurveyListResponse.from(surveyListVos);
    }

    public SurveyHomeResponse toSurveyHomeResponse(Long surveyCount, Long panelCount) {
        return SurveyHomeResponse.of(surveyCount, panelCount);
    }

    public SurveyMyPageCountResponse toSurveyMyPageCountResponse(Long surveyOngoing, Long surveyDone) {
        return SurveyMyPageCountResponse.of(surveyOngoing, surveyDone);
    }

    public SurveyMyPageOrderListResponse toSurveyMyPageOrderListResponse(List<SurveyMyPageOrderVo> surveyMyPageOrderVos) {
        return SurveyMyPageOrderListResponse.from(surveyMyPageOrderVos);
    }

    public SurveyAppHomeListResponse toSurveyAppHomeListResponse(Long panelId, List<SurveyAppHomeVo> surveyAppHomeVos) {
        return null;
    }
}
