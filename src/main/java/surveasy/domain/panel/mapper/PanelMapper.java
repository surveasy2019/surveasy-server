package surveasy.domain.panel.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.response.PanelHomeInfoResponse;
import surveasy.domain.panel.dto.response.PanelIdResponse;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.vo.PanelInfoVo;

@Component
@RequiredArgsConstructor
public class PanelMapper {

    public Panel toEntityExisting(PanelInfoDAO panelInfoDAO, PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO) {
        return Panel.ofExisting(panelInfoDAO, panelInfoFirstSurveyDAO);
    }

    public Panel toEntityNew(PanelSignUpDTO panelSignUpDTO) {
        return Panel.ofNew(panelSignUpDTO);
    }

    public PanelIdResponse toPanelIdResponse(Long panelId) {
        return PanelIdResponse.from(panelId);
    }

    public PanelHomeInfoResponse toPanelHomeInfoResponse(Panel panel, Long count) {
        return PanelHomeInfoResponse.from(panel, count);
    }

    public PanelInfoVo toPanelInfoVo(Panel panel) {
        return PanelInfoVo.from(panel);
    }

    public PanelTokenResponse toPanelTokenResponse(String accessToken, String refreshToken) {
        return PanelTokenResponse.of(accessToken, refreshToken);
    }
}
