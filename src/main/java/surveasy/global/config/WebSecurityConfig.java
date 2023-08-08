package surveasy.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${server.url}")
    private String SERVER_URL;

    private final String[] SwaggerPatterns = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()

                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(SwaggerPatterns).permitAll()
                .requestMatchers("/**").permitAll();

        return http.build();
    }

    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", getDefaulstCorsConfiguration());

        return source;
    }

    protected CorsConfiguration getDefaulstCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:8080",
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
