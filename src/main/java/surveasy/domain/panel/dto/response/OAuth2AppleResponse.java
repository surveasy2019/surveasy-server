package surveasy.domain.panel.dto.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2AppleResponse {

    @NotNull
    private boolean isSignedUp;

    @Nullable
    private PanelTokenResponse tokens;

    @Builder
    private OAuth2AppleResponse(boolean isSignedUp, PanelTokenResponse tokens) {
        this.isSignedUp = isSignedUp;
        if(isSignedUp) this.tokens = tokens;
    }

    public static OAuth2AppleResponse of(boolean isSignedUp, PanelTokenResponse tokens) {
        return OAuth2AppleResponse.builder()
                .isSignedUp(isSignedUp)
                .tokens(tokens)
                .build();
    }
}
