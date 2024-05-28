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
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.global.config.user.PanelDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;
    private final PanelHelper panelHelper;

    @Transactional
    public SurveyIdResponse createSurvey(SurveyServiceDTO surveyServiceDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.createSurvey(surveyServiceDTO));
    }

    @Transactional
    public SurveyHomeResponse getSurveyTotalCount() {
        return surveyMapper.toSurveyHomeResponse(
                surveyHelper.getSurveyTotalCount(), panelHelper.getPanelCount());
    }

    @Transactional
    public SurveyListResponse getSurveyList(Pageable pageable) {
        Page<SurveyListVo> surveyList = surveyRepository.findAllSurveyListVos(pageable);
        return surveyMapper.toSurveyListResponse(surveyList);
    }

    @Transactional
    public SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email) {
        return surveyMapper.toSurveyMyPageOrderListResponse(surveyHelper.getMyPageOrderList(email));
    }

    @Transactional
    public SurveyMyPageCountResponse getMyPageSurveyCounts(String email) {
        return surveyMapper.toSurveyMyPageCountResponse(
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.IN_PROGRESS),
                surveyHelper.getMyPageSurveyCount(email, SurveyStatus.DONE)
        );
    }

    @Transactional
    public SurveyIdResponse editMyPageSurvey(Long id, SurveyMyPageEditDTO surveyMyPageEditDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.editMyPageSurvey(id, surveyMyPageEditDTO));
    }

    @Transactional
    public SurveyIdResponse deleteMyPageSurvey(Long id) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.deleteMyPageSurvey(id));
    }

    @Transactional(readOnly = true)
    public SurveyAppHomeListResponse getSurveyAppHomeList(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        return surveyMapper.toSurveyAppHomeList(surveyHelper.getSurveyAppHomeList(panel), panel.getStatus());
    }

    @Transactional(readOnly = true)
    public SurveyAppListResponse getSurveyAppList(PanelDetails panelDetails, Pageable pageable) {
        final Panel panel = panelDetails.getPanel();
        return surveyMapper.toSurveyAppList(surveyHelper.getSurveyAppList(panel, pageable), panel.getStatus());
    }

    @Transactional(readOnly = true)
    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyHelper.getSurveyAppListDetail(surveyId);
    }

    @Transactional(readOnly = true)
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable) {
        return surveyHelper.getAdminSurveyList(pageable);
    }

    @Transactional(readOnly = true)
    public Survey getAdminSurvey(Long surveyId) {
        return surveyHelper.getAdminSurvey(surveyId);
    }

    @Transactional
    public SurveyIdResponse updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateAdminSurvey(id, surveyAdminDTO));
    }

    @Transactional
    public void deleteAdminSurvey(Long surveyId) {
        surveyHelper.deleteAdminSurvey(surveyId);
    }
}
