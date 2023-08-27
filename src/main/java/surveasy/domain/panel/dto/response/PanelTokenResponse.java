package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PanelTokenResponse {

    private Long panelId;
    private String accessToken;
    private String refreshToken;

    @Builder
    public PanelTokenResponse(Long panelId, String accessToken, String refreshToken) {
        this.panelId = panelId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static PanelTokenResponse of(Long panelId, String accessToken, String refreshToken) {
        return PanelTokenResponse.builder()
                .panelId(panelId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
