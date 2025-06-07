package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.exception.SurveyCannotEdit;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;
import surveasy.domain.user.vo.FirebaseUserVo;
import surveasy.global.common.util.EmailUtils;
import surveasy.global.common.util.FirebaseUtils;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;
    private final PanelHelper panelHelper;
    private final FirebaseUtils firebaseUtils;
    private final EmailUtils emailUtils;

    @Override
    public SurveyIdResponse createSurvey(SurveyCreateRequestDto surveyCreateRequestDto) {
        Survey newSurvey = Survey.createSurvey(surveyCreateRequestDto);
        return surveyMapper.toSurveyIdResponse(surveyHelper.saveSurvey(newSurvey));
    }

    @Override
    public SurveyHomeResponse getSurveyTotalCount() {
        long surveyTotalCount = surveyHelper.getSurveyTotalCount();
        long panelTotalCount = panelHelper.getPanelCount();
        return surveyMapper.toSurveyHomeResponse(surveyTotalCount, panelTotalCount);
    }

    @Override
    public SurveyListResponse getSurveyList(Pageable pageable) {
        Page<SurveyListVo> surveyList = surveyHelper.findAllSurveyListVos(pageable);
        return surveyMapper.toSurveyListResponse(surveyList);
    }

    @Override
    public SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email) {
        List<SurveyMyPageOrderVo> surveyMyPageOrderVoList = surveyHelper.getMyPageOrderList(email);
        return surveyMapper.toSurveyMyPageOrderListResponse(surveyMyPageOrderVoList);
    }

    @Override
    public SurveyMyPageCountResponse getMyPageSurveyCounts(String email) {
        return surveyMapper.toSurveyMyPageCountResponse(
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.IN_PROGRESS),
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.DONE)
        );
    }

    @Override
    public SurveyIdResponse editMyPageSurvey(Long surveyId, SurveyMyPageEditDto surveyMyPageEditDto) {
        Survey survey = surveyHelper.findSurveyById(surveyId);
        if(!surveyHelper.isWaiting(survey.getStatus())) throw SurveyCannotEdit.EXCEPTION;
        survey.updateMySurvey(surveyMyPageEditDto);
        return surveyMapper.toSurveyIdResponse(survey.getId());
    }

    @Override
    public SurveyIdResponse deleteMyPageSurvey(Long id) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.deleteMyPageSurvey(id));
    }

    @Override
    public SurveyAppHomeListResponse getSurveyAppHomeList(Panel panel) {
        return surveyMapper.toSurveyAppHomeList(surveyHelper.getSurveyAppHomeList(panel), panel.getStatus());
    }

    @Override
    public SurveyAppListResponse getSurveyAppList(Panel panel, Pageable pageable) {
        return surveyMapper.toSurveyAppList(surveyHelper.getSurveyAppList(panel, pageable), panel.getStatus());
    }

    @Override
    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyHelper.getSurveyAppListDetail(surveyId);
    }

    @Override
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username) {
        return surveyHelper.getAdminSurveyList(pageable, username);
    }

    @Override
    public SurveyAdminDetailResponse getAdminSurvey(Long surveyId) {
        Survey survey = surveyHelper.getAdminSurvey(surveyId);
        FirebaseUserVo userInfo = firebaseUtils.getFirebaseUserVo(survey.getEmail());
        return SurveyAdminDetailResponse.of(survey, userInfo);
    }

    @Override
    public void sendSurveyWrongAccessMail(Long surveyId) {
        Survey survey = surveyHelper.getAdminSurvey(surveyId);
        emailUtils.sendSurveyWrongAccessMail(survey.getEmail());
    }

    @Override
    public SurveyIdResponse updateAdminSurvey(Long surveyId, AdminSurveyUpdateDto adminSurveyUpdateDto) {
        Survey survey = surveyHelper.findSurveyById(surveyId);
        survey.updateSurvey(adminSurveyUpdateDto);
        if(survey.getSid() == 0 && adminSurveyUpdateDto.status().equals(SurveyStatus.IN_PROGRESS)) {
            survey.updateSurveySid(surveyHelper.findMaxSid() + 1);      // sid 발급
            emailUtils.sendSurveyInProgressMail(survey.getEmail(), survey.getUsername(), survey.getTitle());
        }
        if(adminSurveyUpdateDto.status().equals(SurveyStatus.CANNOT)) {
            emailUtils.sendSurveyCannotMail(survey.getEmail(), survey.getTargetInput(), survey.getPrice());
        }
        return surveyMapper.toSurveyIdResponse(survey.getId());
    }

    @Override
    public void deleteAdminSurvey(Long surveyId) {
        surveyHelper.deleteAdminSurvey(surveyId);
    }
}
