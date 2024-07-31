package surveasy.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import surveasy.global.security.jwt.filter.JwtExceptionHandlerFilter;
import surveasy.global.security.jwt.filter.PanelJwtAuthenticationFilter;
import surveasy.global.security.jwt.filter.UserJwtAuthenticationFilter;
import surveasy.global.security.jwt.*;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${server.url}")
    private String SERVER_URL;
    private final TokenProvider tokenProvider;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final String[] SwaggerPatterns = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    private final String[] AdminGetPatterns = {
            "/survey/admin/**", "/review/admin/**", "/response/admin/**", "/panel/admin/**",
            "/file/**", "/coupon"
    };

    private final String[] AdminPostPatterns = {
            "/coupon"
    };

    private final String[] AdminPatchPatterns = {
            "/survey/admin/**", "/review/admin/**", "/response/admin/**", "/coupon/**"
    };

    private final String[] AdminDeletePatterns = {
            "/survey/admin/**", "/file/**", "/coupon/**"
    };

    private final String[] UserGetPatterns = {
            "/survey/app/**", "/response/**", "/panel/signout", "/panel/home", "/panel/response", "/panel"
    };

    private final String[] UserPostPatterns = {
            "/response/**", "/panel/signup", "/panel/first-survey"
    };

    private final String[] UserPatchPatterns = {
            "/response/**", "/panel"
    };

    private final String[] UserDeletePatterns = {
            "/panel/withdraw"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public UserJwtAuthenticationFilter userJwtAuthenticationFilter() {
        return new UserJwtAuthenticationFilter(tokenProvider);
    }

    public PanelJwtAuthenticationFilter panelJwtAuthenticationFilter() {
        return new PanelJwtAuthenticationFilter(tokenProvider);
    }

    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers((matchers) -> matchers
                        .requestMatchers("/user/**")
                        .requestMatchers("/survey/**")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/user/signin", "/user/signup").permitAll()
                                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/survey/mypage/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/survey").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/survey/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/survey/**").hasAnyAuthority("USER", "ADMIN")
                                .anyRequest().permitAll())
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer
                                .configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(userJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, UserJwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()

                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // JWT 사용하기 때문

                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(SwaggerPatterns).permitAll()
                .requestMatchers(HttpMethod.GET, AdminGetPatterns).hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, AdminPostPatterns).hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, AdminPatchPatterns).hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, AdminDeletePatterns).hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, UserGetPatterns).hasAnyRole("USER", "ANONYMOUS","ADMIN")
                .requestMatchers(HttpMethod.POST, UserPostPatterns).hasAnyRole("USER", "ANONYMOUS","ADMIN")
                .requestMatchers(HttpMethod.PATCH, UserPatchPatterns).hasAnyRole("USER", "ANONYMOUS","ADMIN")
                .requestMatchers(HttpMethod.DELETE, UserDeletePatterns).hasAnyRole("USER", "ANONYMOUS","ADMIN")
                .anyRequest().permitAll()
                .and()
                .headers().frameOptions().disable();


        http.exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilterBefore(panelJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, PanelJwtAuthenticationFilter.class);

        return http.build();
    }

    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", getDefaultCorsConfiguration());

        return source;
    }

    private CorsConfiguration getDefaultCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:8080",
                        "http://localhost:8081",
                        "https://newsurveasyweb.web.app",
                        "https://surveasy-admin.web.app",
                        "https://surveasy-workspace.web.app/#/",
                        "https://surveasy-workspace.web.app",
                        "https://gosurveasy.com/#/",
                        "https://gosurveasy.com",
                        SERVER_URL
                )
        );

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        return configuration;
    }

}
