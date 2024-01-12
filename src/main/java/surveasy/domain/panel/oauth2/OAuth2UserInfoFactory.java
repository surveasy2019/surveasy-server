package surveasy.domain.panel.oauth2;

import surveasy.domain.panel.oauth2.user.KakaoOAuth2User;
import surveasy.domain.panel.oauth2.user.NaverOAuth2User;
import surveasy.domain.panel.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case KAKAO -> {
                return new KakaoOAuth2User(attributes);
            }
            case NAVER -> {
                return new NaverOAuth2User(attributes);
            }
            default -> throw new IllegalArgumentException("Invalid Provider Type");
        }
    }
}
