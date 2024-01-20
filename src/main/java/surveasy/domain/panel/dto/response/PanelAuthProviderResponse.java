package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.option.AuthProvider;

@Getter
public class PanelAuthProviderResponse {

    private AuthProvider authProvider;

    @Builder
    private PanelAuthProviderResponse(AuthProvider authProvider) {
        if(authProvider != null) this.authProvider = authProvider;
        else this.authProvider = AuthProvider.EMAIL;
    }

    public static final PanelAuthProviderResponse from(AuthProvider authProvider) {
        return PanelAuthProviderResponse.builder()
                .authProvider(authProvider)
                .build();
    }
}
