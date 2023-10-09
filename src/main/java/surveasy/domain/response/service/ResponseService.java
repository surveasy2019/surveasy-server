package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
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
}
