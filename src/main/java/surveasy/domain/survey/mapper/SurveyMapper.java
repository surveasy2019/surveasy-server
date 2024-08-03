package surveasy.domain.survey.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppHomeVo;
import surveasy.domain.survey.vo.SurveyAppListVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyMapper {

    public SurveyIdResponse toSurveyIdResponse(Long surveyId) {
        return SurveyIdResponse.from(surveyId);
    }

    public SurveyListResponse toSurveyListResponse(Page<SurveyListVo> surveyListVos) {
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

    public SurveyAppHomeListResponse toSurveyAppHomeList(List<SurveyAppHomeVo> surveyAppHomeVos, PanelStatus status) {
        return SurveyAppHomeListResponse.of(surveyAppHomeVos, status);
    }

    public SurveyAppListResponse toSurveyAppList(Page<SurveyAppListVo> surveyAppList, PanelStatus status) {
        return SurveyAppListResponse.of(surveyAppList, status);
    }

    public SurveyCreateResponseDto toSurveyCreateResponseDto(TossPaymentsResponseDto responseDto) {
        return SurveyCreateResponseDto.of(responseDto);
    }
}
