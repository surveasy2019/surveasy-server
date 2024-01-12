package surveasy.domain.panel.oauth2.user;

import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.Map;

public class KakaoOAuth2User extends OAuth2UserInfo {

    public KakaoOAuth2User(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
    }

    @Override
    public String getOAuth2Id() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");
        return (String) profile.get("nickname");
        // return (String) attributes.get("name");
    }

    @Override
    public String getPhoneNumber() {
        return "";
        // return "0" + ((String) attributes.get("phone_number")).substring(4);
    }

    @Override
    public TargetGender getGender() {
        return TargetGender.ALL;
    }

    @Override
    public LocalDate getBirth() {
        return LocalDate.now();
    }
}
