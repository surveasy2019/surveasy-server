package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AuthProvider;
import surveasy.domain.panel.dto.request.*;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.exception.PanelNotFound;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.panel.repository.PanelRepository;
import surveasy.domain.panel.vo.PanelAdminCsvVo;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.panel.vo.PanelResponseInfoVo;
import surveasy.domain.response.helper.ResponseHelper;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.global.config.jwt.TokenProvider;
import surveasy.global.config.user.PanelDetails;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PanelService {

    private final TokenProvider tokenProvider;
    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;

    private final PanelRepository panelRepository;
    private final ResponseRepository responseRepository;

    private final ResponseHelper responseHelper;

    @Transactional
    public PanelTokenResponse signInEmail(PanelEmailSignInDTO panelEmailSignInDTO) throws ExecutionException, InterruptedException {
        Panel panel = panelHelper.addExistingPanelIfNeed(panelEmailSignInDTO);

        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);

        return panelMapper.toPanelTokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public OAuth2Response oauth2(OAuth2UserInfo oAuth2UserInfo) {
        return panelHelper.createOAuth2Response(oAuth2UserInfo);
    }

    public OAuth2AppleResponse oauth2Apple(AuthIdDTO authIdDTO) {
        return panelHelper.createOauth2AppleResponse(authIdDTO);
    }

    @Transactional
    public PanelTokenResponse signUp(PanelDetails panelDetails, PanelSignUpDTO panelSignUpDTO) {
        Panel panel = panelDetails.getPanel();
        panel = panelHelper.signUp(panel, panelSignUpDTO);

        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        tokenProvider.deleteRefreshToken(panel.getId());
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);

        return panelMapper.toPanelTokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public PanelTokenResponse signUpApple(PanelAppleSignUpDTO panelAppleSignUpDTO) {
        return panelHelper.signUpApple(panelAppleSignUpDTO);
    }

    @Transactional
    public PanelIdResponse doFirstSurvey(PanelDetails panelDetails, PanelFirstSurveyDTO panelFirstSurveyDTO) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelIdResponse(panelHelper.doFirstSurvey(panel, panelFirstSurveyDTO));
    }

    @Transactional(readOnly = true)
    public PanelHomeInfoResponse getPanelHomeInfo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        long count = responseHelper.getPanelResponseCount(panel.getId());
        return panelMapper.toPanelHomeInfoResponse(panel, count);
    }

    @Transactional(readOnly = true)
    public PanelResponseInfoVo getPanelResponseInfoVo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelResponseInfoVo(panel);
    }

    @Transactional
    public PanelInfoVo updatePanelInfo(PanelDetails panelDetails, PanelInfoUpdateDTO panelInfoUpdateDTO) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelInfoVo(panelHelper.updatePanelInfo(panel, panelInfoUpdateDTO));
    }


    @Transactional(readOnly = true)
    public PanelInfoVo getPanelInfoVo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelInfoVo(panel);
    }

    @Transactional(readOnly = true)
    public PanelAdminListResponse getAdminPanelList(Pageable pageable) {
        return panelHelper.getAdminPanelList(pageable);
    }

    @Transactional
    public PanelTokenResponse reissueToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        final String refreshToken = refreshTokenRequestDTO.getRefreshToken();
        final Panel panel = panelHelper.findPanelById(Long.parseLong(tokenProvider.getTokenPanelId(refreshToken)));
        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);

        tokenProvider.validateRefreshToken(refreshToken);
        panelHelper.matchesRefreshToken(refreshToken, panel);

        final String newAccessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        return panelMapper.toPanelTokenResponse(newAccessToken, refreshToken);
    }

    public void signOut(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        tokenProvider.deleteRefreshToken(panel.getId());
    }

    @Transactional
    public PanelAuthProviderResponse withdraw(PanelDetails panelDetails) {
        signOut(panelDetails);
        final Panel panel = panelDetails.getPanel();
        responseRepository.deleteAllByPanelId(panel.getId());
        panelRepository.delete(panel);
        return panelMapper.toPanelAuthProviderResponse(panel.getAuthProvider());
    }

    public String reissueAccessToken(Long panelId) {
        final Panel panel = panelRepository.findById(panelId)
                .orElseThrow(() -> PanelNotFound.EXCEPTION);
        return tokenProvider.createAccessToken(panelId, tokenProvider.panelAuthorizationInput(panel));
    }

    public PanelAdminCsvListResponse getAdminPanelCsvList() {
        List<PanelAdminCsvVo> panelAdminCsvVoList = panelHelper.findAllPanelAdminCsvVos();
        return panelMapper.toPanelAdminCsvListResponse(panelAdminCsvVoList);
    }
}
