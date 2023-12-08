package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.global.config.user.PanelDetails;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

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
    public SurveyListResponse getSurveyList() {
        List<SurveyListVo> surveyList = surveyRepository.findAllSurveyListVos();
        return surveyMapper.toSurveyListResponse(surveyList);
    }

    @Transactional
    public SurveyMyPageOrderListResponse getSurveyMyPageOrderList(String email) {
        return surveyMapper.toSurveyMyPageOrderListResponse(surveyHelper.getMyPageOrderList(email));
    }

    @Transactional
    public SurveyMyPageCountResponse getMyPageSurveyCounts(String email) {
        return surveyMapper.toSurveyMyPageCountResponse(
                surveyHelper.getMyPageSurveyOngoingCount(email),
                surveyHelper.getMyPageSurveyDoneCount(email)
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

    @Transactional
    public SurveyAppHomeListResponse getSurveyAppHomeListResponse(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        return surveyMapper.toSurveyAppHomeListResponse(panel.getId(), surveyHelper.getSurveyListProgressEq2(panel.getId()));
    }

    @Transactional
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable) {
        return surveyHelper.getAdminSurveyList(pageable);
    }

    @Transactional
    public SurveyIdResponse updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateAdminSurvey(id, surveyAdminDTO));
    }
}
