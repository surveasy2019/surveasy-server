package surveasy.domain.panel.domain.option;

import lombok.Getter;

@Getter
public enum AuthProvider {

    EMAIL("이메일"),
    KAKAO("카카오"),
    APPLE("애플");

    private final String value;

    AuthProvider(String value) {
        this.value = value;
    }
}
