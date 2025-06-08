package surveasy.domain.survey.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;

@Service
public interface SurveyService {
    SurveyHomeResponse getSurveyTotalCount();

    SurveyIdResponse createSurvey(SurveyCreateRequestDto surveyCreateRequestDto);

    SurveyListResponse getSurveyList(Pageable pageable);

    SurveyMyPageCountResponse getMyPageSurveyCounts(String email);

    SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email);

    SurveyIdResponse editMyPageSurvey(Long surveyId, SurveyMyPageEditDto surveyMyPageEditDTO);

    SurveyIdResponse deleteMyPageSurvey(Long surveyId);

    SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username);

    SurveyAdminDetailResponse getAdminSurvey(Long surveyId);

    void sendSurveyWrongAccessMail(Long surveyId);

    SurveyIdResponse updateAdminSurvey(Long surveyId, AdminSurveyUpdateDto adminSurveyUpdateDto);

    void deleteAdminSurvey(Long surveyId);

    SurveyAppHomeListResponse getSurveyAppHomeList(Panel panel);

    SurveyAppListResponse getSurveyAppList(Panel panel, Pageable pageable);

    SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId);
}
