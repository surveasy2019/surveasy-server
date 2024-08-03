package surveasy.domain.survey.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.user.domain.User;

@Service
public interface SurveyService {
    SurveyHomeResponse getSurveyTotalCount();

    SurveyCreateResponseDto createSurvey(User user, SurveyCreateRequestDto surveyCreateRequestDto);

    SurveyListResponse getSurveyList(Pageable pageable);

    SurveyMyPageCountResponse getMyPageSurveyCounts(User user);

    SurveyMyPageOrderListResponse getSurveyMyPageOrderList(User user);

    SurveyIdResponse updateSurvey(Long surveyId, SurveyUpdateRequestDto surveyUpdateRequestDto, User user);

    SurveyIdResponse deleteSurvey(Long surveyId, User user);

    SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username);

    Survey getAdminSurvey(Long surveyId);

    SurveyIdResponse updateAdminSurvey(Long surveyId, AdminSurveyUpdateRequestDto adminSurveyUpdateRequestDto);

    void deleteAdminSurvey(Long surveyId);

    SurveyAppHomeListResponse getSurveyAppHomeList(Panel panel);

    SurveyAppListResponse getSurveyAppList(Panel panel, Pageable pageable);

    SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId);
}
