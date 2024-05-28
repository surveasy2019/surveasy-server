package surveasy.domain.panel.service;

import org.springframework.data.domain.Pageable;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;

import java.util.concurrent.ExecutionException;

public interface PanelService {
    PanelTokenResponse signInEmail(PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException;

    OAuth2Response oauth2(OAuth2UserInfo oAuth2UserInfo);

    OAuth2AppleResponse oauth2Apple(AuthIdDTO oauthIdDTO);

    PanelTokenResponse signUp(Panel panel, PanelSignUpDTO panelSignUpDTO);

    PanelTokenResponse signUpApple(PanelAppleSignUpDTO panelAppleSignUpDTO);

    PanelIdResponse doFirstSurvey(Panel panel, PanelFirstSurveyDTO panelFirstSurveyDTO);

    PanelTokenResponse reissueToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

    void signOut(Panel panel);

    PanelAuthProviderResponse withdraw(Panel panel);

    PanelHomeInfoResponse getPanelHomeInfo(Panel panel);

    PanelResponseInfoVo getPanelResponseInfoVo(Panel panel);

    PanelInfoVo getPanelInfoVo(Panel panel);

    PanelInfoVo updatePanelInfo(Panel panel, PanelInfoUpdateDTO panelInfoUpdateDTO);

    PanelAdminListResponse getAdminPanelList(Pageable pageable);

    PanelAdminCsvListResponse getAdminPanelCsvList();
}
