package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseImgUrlUpdateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.helper.ResponseHelper;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.global.config.user.PanelDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseHelper responseHelper;
    private final ResponseMapper responseMapper;

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
    public ResponseIdResponse updateResponseImgUrl(PanelDetails panelDetails, Long responseId, ResponseImgUrlUpdateRequestDTO responseImgUrlUpdateRequestDTO) {
        final Panel panel = panelDetails.getPanel();
        return responseMapper.toResponseIdResponse(responseHelper.updateResponseImgUrl(panel, responseId, responseImgUrlUpdateRequestDTO));
    }
}
