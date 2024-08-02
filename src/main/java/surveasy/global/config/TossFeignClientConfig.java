package surveasy.global.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import surveasy.global.common.util.toss.TossSecretKeyGenerator;
import surveasy.global.error.toss.TossErrorDecoder;

@EnableFeignClients("surveasy.global.common.util.toss")
@RequiredArgsConstructor
@Configuration
public class TossFeignClientConfig {
    private final TossSecretKeyGenerator tossSecretKeyGenerator;

    public RequestInterceptor tossFeignRequestInterceptor() {
        String authorization = tossSecretKeyGenerator.generateSecretKey();
        return requestTemplate -> {
            requestTemplate.header("Authorization", authorization);
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new TossErrorDecoder();
    }
}
