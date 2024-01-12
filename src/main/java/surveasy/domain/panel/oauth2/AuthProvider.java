package surveasy.domain.panel.oauth2;

import lombok.Getter;

@Getter
public enum AuthProvider {

    KAKAO("카카오"),
    NAVER("네이버");

    private final String value;

    AuthProvider(String value) {
        this.value = value;
    }
}
