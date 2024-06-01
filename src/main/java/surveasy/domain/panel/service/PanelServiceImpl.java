package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.vo.PanelAdminCsvVo;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;
import surveasy.domain.response.helper.ResponseHelper;
import surveasy.global.config.jwt.TokenProvider;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PanelServiceImpl implements PanelService {

    private final TokenProvider tokenProvider;
    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;
    private final ResponseHelper responseHelper;

    public PanelTokenResponse signInEmail(PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException {
        Panel panel = panelHelper.addExistingPanelIfNeed(panelEmailSignInDTO);
        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);
        return panelMapper.toPanelTokenResponse(accessToken, refreshToken);
    }

    public OAuth2Response oauth2(OAuth2UserInfo oAuth2UserInfo) {
        return panelHelper.createOAuth2Response(oAuth2UserInfo);
    }

    public OAuth2AppleResponse oauth2Apple(AuthIdDTO authIdDTO) {
        return panelHelper.createOauth2AppleResponse(authIdDTO);
    }

    public PanelTokenResponse signUp(Panel panel, PanelSignUpDTO panelSignUpDTO) {
        panel = panelHelper.signUp(panel, panelSignUpDTO);
        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        tokenProvider.deleteRefreshToken(panel.getId());
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);
        return panelMapper.toPanelTokenResponse(accessToken, refreshToken);
    }

    public PanelTokenResponse signUpApple(PanelAppleSignUpDTO panelAppleSignUpDTO) {
        return panelHelper.signUpApple(panelAppleSignUpDTO);
    }

    public PanelIdResponse doFirstSurvey(Panel panel, PanelFirstSurveyDTO panelFirstSurveyDTO) {
        return panelMapper.toPanelIdResponse(panelHelper.doFirstSurvey(panel, panelFirstSurveyDTO));
    }

    public PanelHomeInfoResponse getPanelHomeInfo(Panel panel) {
        long count = responseHelper.getPanelResponseCount(panel.getId());
        return panelMapper.toPanelHomeInfoResponse(panel, count);
    }

    public PanelResponseInfoVo getPanelResponseInfoVo(Panel panel) {
        return panelMapper.toPanelResponseInfoVo(panel);
    }

    public PanelInfoVo updatePanelInfo(Panel panel, PanelInfoUpdateDTO panelInfoUpdateDTO) {
        return panelMapper.toPanelInfoVo(panelHelper.updatePanelInfo(panel, panelInfoUpdateDTO));
    }

    public PanelInfoVo getPanelInfoVo(Panel panel) {
        return panelMapper.toPanelInfoVo(panel);
    }

    public PanelAdminListResponse getAdminPanelList(Pageable pageable, String keyword) {
        return panelHelper.getAdminPanelList(pageable, keyword);
    }

    public PanelTokenResponse reissueToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        final String refreshToken = refreshTokenRequestDTO.getRefreshToken();
        final Panel panel = panelHelper.findPanelById(Long.parseLong(tokenProvider.getTokenPanelId(refreshToken)));
        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        tokenProvider.validateRefreshToken(refreshToken);
        panelHelper.matchesRefreshToken(refreshToken, panel);
        final String newAccessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        return panelMapper.toPanelTokenResponse(newAccessToken, refreshToken);
    }

    public void signOut(Panel panel) {
        tokenProvider.deleteRefreshToken(panel.getId());
    }

    public PanelAuthProviderResponse withdraw(Panel panel) {
        signOut(panel);
        responseHelper.deleteAllByPanelId(panel.getId());
        panelHelper.deletePanel(panel);
        return panelMapper.toPanelAuthProviderResponse(panel.getAuthProvider());
    }

    public PanelAdminCsvListResponse getAdminPanelCsvList() {
        List<PanelAdminCsvVo> panelAdminCsvVoList = panelHelper.findAllPanelAdminCsvVos();
        return panelMapper.toPanelAdminCsvListResponse(panelAdminCsvVoList);
    }
}
