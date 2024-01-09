package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PanelTokenResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    public PanelTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static PanelTokenResponse of(String accessToken, String refreshToken) {
        return PanelTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
