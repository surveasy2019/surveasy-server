package surveasy.global.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
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
import surveasy.global.config.user.UserDetailsServiceImpl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final UserDetailsServiceImpl userDetailsService;

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

    public String createAccessToken(Long id, Authentication authentication) {
        return createToken(id, accessTokenExpirationTime, authentication);
    }

    public String createRefreshToken(Long id, Authentication authentication) {
        String refreshToken = createToken(id, refreshTokenExpirationTIme, authentication);
        // redisUtil.setRedisRefreshToken(id, refreshToken);

        return refreshToken;
    }

    private String createToken(Long id, int expirationTime, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expirationTime);   // 만료까지 소요 시간

        final Date issuedAt = new Date();
        final Date validity = new Date(calendar.getTimeInMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(id.toString())
                .claim("auth", authorities)
                .setIssuedAt(issuedAt)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getTokenPanelId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
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


    public Authentication panelAuthorizationInput(Panel panel) {
        UserDetails userDetails = userDetailsService.loadUserByUserId(panel.getId());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUserId(Long.parseLong(getTokenPanelId(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

}
