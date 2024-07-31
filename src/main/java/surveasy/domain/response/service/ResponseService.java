package surveasy.domain.response.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.AdminSurveyResponseListResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.global.security.user.PanelDetails;

@Service
public interface ResponseService {
    ResponseIdResponse createResponse(Panel panel, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO);

    ResponseHistoryListResponse getResponseMyPageList(Panel panel, String type, Pageable pageable);

    ResponseIdResponse updateResponseImgUrl(Panel panel, Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO);

    ResponseIdResponse updateResponseAdmin(Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO);

    AdminSurveyResponseListResponse getAdminSurveyResponseList(Long surveyId);

    void doneAggregation();
}
