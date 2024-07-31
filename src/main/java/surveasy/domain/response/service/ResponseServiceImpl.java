package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.AdminSurveyResponseListResponse;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.helper.ResponseHelper;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.global.security.user.PanelDetails;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ResponseServiceImpl implements ResponseService {
    private final ResponseHelper responseHelper;
    private final ResponseMapper responseMapper;

    @Override
    public ResponseIdResponse createResponse(Panel panel, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO) {
        return responseMapper.toResponseIdResponse(responseHelper.createResponse(panel, surveyId, responseCreateRequestDTO));
    }

    @Override
    public ResponseHistoryListResponse getResponseMyPageList(Panel panel, String type, Pageable pageable) {
        return responseHelper.getResponseMyPageList(panel.getId(), type, pageable);
    }

    @Override
    public ResponseIdResponse updateResponseImgUrl(Panel panel, Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        return responseMapper.toResponseIdResponse(responseHelper.updateResponseImgUrl(panel, responseId, responseUpdateRequestDTO));
    }

    @Override
    public ResponseIdResponse updateResponseAdmin(Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        return responseMapper.toResponseIdResponse(responseHelper.updateResponseAdmin(responseId, responseUpdateRequestDTO));
    }

    @Override
    public AdminSurveyResponseListResponse getAdminSurveyResponseList(Long surveyId) {
        return responseMapper.toAdminSurveyResponseListResponse(responseHelper.getAdminSurveyResponseList(surveyId));
    }

    @Override
    public void doneAggregation() {
        responseHelper.doneAggregation();
    }
}
