package surveasy.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import surveasy.global.error.exception.TokenValidateException;
import surveasy.global.security.jwt.TokenProvider;

import java.io.IOException;

public class PanelJwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    public PanelJwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = tokenProvider.resolveToken(request);

        try {
            if(jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if(tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getPanelAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw TokenValidateException.EXCEPTION;
            }

        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
