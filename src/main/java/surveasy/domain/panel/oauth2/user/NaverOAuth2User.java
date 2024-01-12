package surveasy.domain.panel.oauth2.user;

import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.Map;

public class NaverOAuth2User extends OAuth2UserInfo {

    public NaverOAuth2User(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
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
        return (String) attributes.get("name");
    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("mobile");
    }

    @Override
    public TargetGender getGender() {
        return null;
    }

    @Override
    public LocalDate getBirth() {
        return null;
    }
}
