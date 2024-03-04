package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.batch.BatchScheduler;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.AdminSurveyResponseListResponse;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.helper.ResponseHelper;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.global.config.user.PanelDetails;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseHelper responseHelper;
    private final ResponseMapper responseMapper;

    private final BatchScheduler batchScheduler;

    @Transactional
    public ResponseIdResponse createResponse(PanelDetails panelDetails, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO) {
        final Panel panel = panelDetails.getPanel();
        return responseMapper.toResponseIdResponse(responseHelper.createResponse(panel, surveyId, responseCreateRequestDTO));
    }

    @Transactional(readOnly = true)
    public ResponseHistoryListResponse getResponseMyPageList(PanelDetails panelDetails, String type, Pageable pageable) {
        final Panel panel = panelDetails.getPanel();
        return responseHelper.getResponseMyPageList(panel.getId(), type, pageable);
    }

    @Transactional
    public ResponseIdResponse updateResponseImgUrl(PanelDetails panelDetails, Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        final Panel panel = panelDetails.getPanel();
        return responseMapper.toResponseIdResponse(responseHelper.updateResponseImgUrl(panel, responseId, responseUpdateRequestDTO));
    }

    @Transactional
    public ResponseIdResponse updateResponseAdmin(Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        return responseMapper.toResponseIdResponse(responseHelper.updateResponseAdmin(responseId, responseUpdateRequestDTO));
    }

    @Transactional(readOnly = true)
    public AdminSurveyResponseListResponse getAdminSurveyResponseList(Long surveyId) {
        return responseMapper.toAdminSurveyResponseListResponse(responseHelper.getAdminSurveyResponseList(surveyId));
    }

    public void doneAggregation() {
        responseHelper.doneAggregation();
    }

    public void doAggregation() throws Exception {
        batchScheduler.batchSchedulerTest();
    }
}
