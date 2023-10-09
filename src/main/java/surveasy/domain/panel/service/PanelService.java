package surveasy.domain.panel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.dto.request.PanelInfoUpdateDTO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.dto.request.PanelUidDTO;
import surveasy.domain.panel.dto.response.*;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.panel.mapper.PanelMapper;
import surveasy.domain.response.helper.ResponseHelper;
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

    private final ResponseHelper responseHelper;

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
    public PanelHomeInfoResponse getPanelHomeInfo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        Long count = responseHelper.getPanelResponseCount(panel.getId());

        return panelMapper.toPanelHomeInfoResponse(panel, count);
    }

    @Transactional
    public PanelMyPageInfoResponse updatePanelInfo(PanelDetails panelDetails, PanelInfoUpdateDTO panelInfoUpdateDTO) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelMyPageInfoResponse(panelHelper.updatePanelInfo(panel, panelInfoUpdateDTO));
    }


    @Transactional(readOnly = true)
    public PanelMyPageInfoResponse getPanelMyPageInfo(PanelDetails panelDetails) {
        final Panel panel = panelDetails.getPanel();
        return panelMapper.toPanelMyPageInfoResponse(panel);
    }

    @Transactional(readOnly = true)
    public PanelAdminListResponse getAdminPanelList(Pageable pageable) {
        return panelHelper.getAdminPanelList(pageable);
    }

}
