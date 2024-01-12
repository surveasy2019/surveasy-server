package surveasy.domain.panel.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.oauth2.user.OAuth2UserInfo;
import surveasy.domain.panel.repository.PanelRepository;
import surveasy.global.config.user.PanelDetails;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final PanelRepository panelRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        if(!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new RuntimeException("Email Not Found From OAuth2 Provider");
        }

        Panel panel = panelRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);

        // 이미 가입된 패널
        if(panel != null) {

            // 이메일-PW 회원가입했던 메일 or 다른 provider에서 시도
            if(panel.getAuthProvider() == null || !panel.getAuthProvider().equals(authProvider)) {
                PanelDetails panelDetails = new PanelDetails(panel);
                panelDetails.setDuplicateEmail(true);
                return panelDetails;
            }

            // 동일 provider에서 시도
            else {
                panel = updatePanel(panel, oAuth2UserInfo);
            }
        }

        // 신규 패널 가입
        else {
            panel = registerPanel(oAuth2UserInfo, authProvider);
        }

        return new PanelDetails(panel);
    }

    private Panel registerPanel(OAuth2UserInfo oAuth2UserInfo, AuthProvider authProvider) {
        Panel panel = Panel.ofOAuth(oAuth2UserInfo, authProvider);
        return panelRepository.save(panel);
    }

    private Panel updatePanel(Panel panel, OAuth2UserInfo oAuth2UserInfo) {
        return panelRepository.save(panel.updateFrom(oAuth2UserInfo));
    }
}
