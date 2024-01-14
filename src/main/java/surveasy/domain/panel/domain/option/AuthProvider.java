package surveasy.domain.panel.domain.option;

import lombok.Getter;

@Getter
public enum AuthProvider {
    KAKAO("카카오"),
    APPPLE("애플");

    private final String value;

    AuthProvider(String value) {
        this.value = value;
    }
}
