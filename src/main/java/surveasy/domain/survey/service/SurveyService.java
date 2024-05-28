package surveasy.domain.survey.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.global.config.user.PanelDetails;

@Service
public interface SurveyService {
    SurveyHomeResponse getSurveyTotalCount();

    SurveyIdResponse createSurvey(SurveyCreateRequestDto surveyCreateRequestDto);

    SurveyListResponse getSurveyList(Pageable pageable);

    SurveyMyPageCountResponse getMyPageSurveyCounts(String email);

    SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email);

    SurveyIdResponse editMyPageSurvey(Long surveyId, SurveyMyPageEditDTO surveyMyPageEditDTO);

    SurveyIdResponse deleteMyPageSurvey(Long surveyId);

    SurveyAdminListResponse getAdminSurveyList(Pageable pageable);

    Survey getAdminSurvey(Long surveyId);

    SurveyIdResponse updateAdminSurvey(Long surveyId, SurveyAdminDTO surveyAdminDTO);

    void deleteAdminSurvey(Long surveyId);

    SurveyAppHomeListResponse getSurveyAppHomeList(PanelDetails panelDetails);

    SurveyAppListResponse getSurveyAppList(PanelDetails panelDetails, Pageable pageable);

    SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId);
}
