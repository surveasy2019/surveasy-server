package surveasy.global.config.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import surveasy.domain.panel.dto.response.PanelTokenResponse;
import surveasy.domain.panel.exception.Oauth2DuplicateUser;
import surveasy.domain.panel.oauth2.CookieAuthorizationRequestRepository;
import surveasy.global.config.jwt.TokenProvider;
import surveasy.global.config.user.PanelDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${server.url}")
    private String serverUrl;
    private final TokenProvider tokenProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl;
        if(((PanelDetails) authentication.getPrincipal()).isDuplicateEmail())
            targetUrl = determineTargetUrlException(request, response);
        else
            targetUrl = determineTargetUrl(request, response, authentication);

        if(response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = serverUrl + "/panel/oauth2";

        PanelTokenResponse tokenResponse = PanelTokenResponse.of(
                tokenProvider.createOAuth2AccessToken(authentication),
                tokenProvider.createOAuth2RefreshToken(authentication)
        );

        String result = null;

        try {
            result = objectMapper.writeValueAsString(tokenResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UriComponentsBuilder.fromHttpUrl(targetUrl)
                .queryParam("result", result)
                .build().encode().toUriString();
    }

    protected String determineTargetUrlException(HttpServletRequest request, HttpServletResponse response) {
        String targetUrl = serverUrl + "/panel/oauth2";
        String error = null;

        try {
            error = objectMapper.writeValueAsString(Oauth2DuplicateUser.EXCEPTION.getErrorReason().getReason());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return UriComponentsBuilder.fromHttpUrl(targetUrl)
                .queryParam("error", error)
                .build().encode().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieAuthorizationRequestRepository.removeAuthorizationRequest(request, response);
    }
}
