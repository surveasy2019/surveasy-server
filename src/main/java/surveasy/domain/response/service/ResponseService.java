package surveasy.domain.response.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.AdminSurveyResponseListResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.global.security.user.PanelDetails;

@Service
public interface ResponseService {
    ResponseIdResponse createResponse(PanelDetails panelDetails, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO);

    ResponseHistoryListResponse getResponseMyPageList(PanelDetails panelDetails, String type, Pageable pageable);

    ResponseIdResponse updateResponseImgUrl(PanelDetails panelDetails, Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO);

    ResponseIdResponse updateResponseAdmin(Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO);

    AdminSurveyResponseListResponse getAdminSurveyResponseList(Long surveyId);

    void doneAggregation();
}
