package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.PanelAdminListResponse;
import surveasy.domain.panel.dto.response.PanelInfoResponse;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.global.config.jwt.TokenProvider;
import surveasy.global.config.user.PanelDetails;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PanelService {

    private final TokenProvider tokenProvider;
    private final PanelHelper panelHelper;
    private final PanelMapper panelMapper;

    @Transactional
    public PanelTokenResponse signUpExisting(PanelUidDTO panelUidDTO) throws ParseException, ExecutionException, InterruptedException {
        Panel panel = panelHelper.addExistingPanelIfNeed(panelUidDTO);

        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);

        return panelMapper.toPanelTokenResponse(panel.getId(), accessToken, refreshToken);
    }

    @Transactional
    public PanelTokenResponse signUpNew(PanelSignUpDTO panelSignUpDTO) {
        Panel panel = panelHelper.addNewPanelIfNeed(panelSignUpDTO);

        final Authentication authentication = tokenProvider.panelAuthorizationInput(panel);
        final String accessToken = tokenProvider.createAccessToken(panel.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(panel.getId(), authentication);

        return panelMapper.toPanelTokenResponse(panel.getId(), accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public PanelInfoResponse getPanelInfo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        Long count = panelHelper.getPanelResponseCount(panel.getId());

        return panelMapper.toPanelInfoResponse(panel, count);
    }

    @Transactional
    public PanelAdminListResponse getAdminPanelList(Pageable pageable) {
        return panelHelper.getAdminPanelList(pageable);
    }

}
