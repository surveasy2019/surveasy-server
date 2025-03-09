package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;
import surveasy.domain.user.vo.FirebaseUserVo;
import surveasy.global.common.util.FirebaseUtils;
import surveasy.global.config.user.PanelDetails;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;
    private final PanelHelper panelHelper;
    private final FirebaseUtils firebaseUtils;

    public SurveyIdResponse createSurvey(SurveyCreateRequestDto surveyCreateRequestDto) {
        Survey newSurvey = surveyMapper.toEntity(surveyCreateRequestDto);
        return surveyMapper.toSurveyIdResponse(surveyHelper.saveSurvey(newSurvey));
    }

    public SurveyHomeResponse getSurveyTotalCount() {
        long surveyTotalCount = surveyHelper.getSurveyTotalCount();
        long panelTotalCount = panelHelper.getPanelCount();
        return surveyMapper.toSurveyHomeResponse(surveyTotalCount, panelTotalCount);
    }

    public SurveyListResponse getSurveyList(Pageable pageable) {
        Page<SurveyListVo> surveyList = surveyHelper.findAllSurveyListVos(pageable);
        return surveyMapper.toSurveyListResponse(surveyList);
    }

    public SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email) {
        List<SurveyMyPageOrderVo> surveyMyPageOrderVoList = surveyHelper.getMyPageOrderList(email);
        return surveyMapper.toSurveyMyPageOrderListResponse(surveyMyPageOrderVoList);
    }

    public SurveyMyPageCountResponse getMyPageSurveyCounts(String email) {

        return surveyMapper.toSurveyMyPageCountResponse(
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.IN_PROGRESS),
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.DONE)
        );
    }

    public SurveyIdResponse editMyPageSurvey(Long id, SurveyMyPageEditDTO surveyMyPageEditDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.editMyPageSurvey(id, surveyMyPageEditDTO));
    }

    public SurveyIdResponse deleteMyPageSurvey(Long id) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.deleteMyPageSurvey(id));
    }

    public SurveyAppHomeListResponse getSurveyAppHomeList(Panel panel) {
        return surveyMapper.toSurveyAppHomeList(surveyHelper.getSurveyAppHomeList(panel), panel.getStatus());
    }

    public SurveyAppListResponse getSurveyAppList(Panel panel, Pageable pageable) {
        return surveyMapper.toSurveyAppList(surveyHelper.getSurveyAppList(panel, pageable), panel.getStatus());
    }

    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyHelper.getSurveyAppListDetail(surveyId);
    }

    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username) {
        return surveyHelper.getAdminSurveyList(pageable, username);
    }

    public SurveyAdminDetailResponse getAdminSurvey(Long surveyId) {
        Survey survey = surveyHelper.getAdminSurvey(surveyId);
        FirebaseUserVo userInfo = firebaseUtils.getFirebaseUserVo(survey.getEmail());
        return SurveyAdminDetailResponse.of(survey, userInfo);
    }

    public SurveyIdResponse updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateAdminSurvey(id, surveyAdminDTO));
    }

    public void deleteAdminSurvey(Long surveyId) {
        surveyHelper.deleteAdminSurvey(surveyId);
    }
}
