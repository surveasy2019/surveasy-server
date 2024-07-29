package surveasy.global.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.exception.NotRefreshToken;
import surveasy.domain.panel.util.RedisUtil;
import surveasy.domain.user.domain.User;
import surveasy.global.common.enm.AuthType;
import surveasy.global.common.enm.TokenType;
import surveasy.global.config.user.CustomUserDetails;
import surveasy.global.config.user.CustomUserDetailsService;
import surveasy.global.config.user.PanelDetails;
import surveasy.global.config.user.PanelDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final PanelDetailsService panelDetailsService;
    private final CustomUserDetailsService userDetailsService;
    private final RedisUtil redisUtil;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String ACCESS_KEY = "access";
    private static final String REFRESH_KEY = "refresh";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.jwt.secret}")
    private String secret;

    private Key key;

    @Value("${spring.jwt.access-token-validity-in-seconds}")
    private int accessTokenExpirationTime;

    @Value("${spring.jwt.refresh-token-validity-in-seconds}")
    private int refreshTokenExpirationTIme;

    @Override
    public void afterPropertiesSet() {
        byte keyBytes[] = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(AuthType authType, Long id, Authentication authentication) {
        return createToken(authType, TokenType.ACCESS, id, accessTokenExpirationTime, authentication);
    }

    public String createRefreshToken(AuthType authType, Long id, Authentication authentication) {
        return createToken(authType, TokenType.REFRESH, id, refreshTokenExpirationTIme, authentication);
    }

    private String createToken(AuthType authType, TokenType tokenType, Long id, int expirationTime, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expirationTime);   // 만료까지 소요 시간

        final Date issuedAt = new Date();
        final Date validity = new Date(calendar.getTimeInMillis());

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("type", tokenType.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        if(tokenType.equals(TokenType.REFRESH)) {
            redisUtil.setRefreshToken(authType, id, token, expirationTime);
        }

        return token;
    }

    public void deleteRefreshToken(Long id) {
        redisUtil.delete(id.toString());
    }

    public String getTokenPanelId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getUserAuthentication(String token) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUserId(Long.parseLong(getTokenPanelId(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public Authentication getPanelAuthentication(String token) {
        PanelDetails panelDetails = (PanelDetails) panelDetailsService.loadUserByUserId(Long.parseLong(getTokenPanelId(token)));
        return new UsernamePasswordAuthenticationToken(panelDetails, token, panelDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 JWT 토큰입니다");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }

        return false;
    }

    public void validateRefreshToken(String refreshToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();
        String type = claims.get("type", String.class);

        if(!type.equals("refresh")) {
            throw NotRefreshToken.EXCEPTION;
        }
    }

    public Authentication userAuthorizationInput(User user) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUserId(user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public Authentication panelAuthorizationInput(Panel panel) {
        PanelDetails panelDetails = (PanelDetails) panelDetailsService.loadUserByUserId(panel.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                panelDetails,
                "",
                panelDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
