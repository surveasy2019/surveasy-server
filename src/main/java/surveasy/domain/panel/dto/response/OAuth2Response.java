package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Response {

    private boolean isSignedUp;

    private boolean additionalInfo;

    private PanelTokenResponse tokens;


    @Builder
    private OAuth2Response(boolean isSignedUp, boolean additionalInfo, PanelTokenResponse tokens) {
        this.isSignedUp = isSignedUp;
        this.additionalInfo = additionalInfo;
        this.tokens = tokens;
    }

    public static final OAuth2Response of(boolean isSignedUp, boolean additionalInfo, String accessToken, String refreshToken) {
        return OAuth2Response.builder()
                .isSignedUp(isSignedUp)
                .additionalInfo(additionalInfo)
                .tokens(PanelTokenResponse.of(accessToken, refreshToken))
                .build();
    }
}
