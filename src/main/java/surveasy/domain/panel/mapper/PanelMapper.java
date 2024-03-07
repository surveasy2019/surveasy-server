package surveasy.domain.panel.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AuthProvider;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;

@Component
@RequiredArgsConstructor
public class PanelMapper {

    public Panel toEntityExisting(PanelInfoDAO panelInfoDAO, PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO) {
        return Panel.ofExisting(panelInfoDAO, panelInfoFirstSurveyDAO);
    }

    public Panel toEntityNew(OAuth2UserInfo oAuth2UserInfo) {
        return Panel.ofOAuth2(oAuth2UserInfo);
    }

    public Panel toEntityNewApple(PanelAppleSignUpDTO panelAppleSignUpDTO) {
        return Panel.ofOAuth2Apple(panelAppleSignUpDTO);
    }

    public OAuth2Response toOAuth2Response(boolean existingPanel, boolean additionalInfo, String accessToken, String refreshToken) {
        return OAuth2Response.of(existingPanel, additionalInfo, accessToken, refreshToken);
    }

    public OAuth2AppleResponse toOAuth2AppleResponse(boolean isSignedUp, PanelTokenResponse tokens) {
        return OAuth2AppleResponse.of(isSignedUp, tokens);
    }

    public PanelIdResponse toPanelIdResponse(Long panelId) {
        return PanelIdResponse.from(panelId);
    }

    public PanelHomeInfoResponse toPanelHomeInfoResponse(Panel panel, Long count) {
        return PanelHomeInfoResponse.from(panel, count);
    }

    public PanelResponseInfoVo toPanelResponseInfoVo(Panel panel) {
        return PanelResponseInfoVo.from(panel);
    }

    public PanelInfoVo toPanelInfoVo(Panel panel) {
        return PanelInfoVo.from(panel);
    }

    public PanelTokenResponse toPanelTokenResponse(String accessToken, String refreshToken) {
        return PanelTokenResponse.of(accessToken, refreshToken);
    }

    public PanelAuthProviderResponse toPanelAuthProviderResponse(AuthProvider authProvider) {
        return PanelAuthProviderResponse.from(authProvider);
    }
}
