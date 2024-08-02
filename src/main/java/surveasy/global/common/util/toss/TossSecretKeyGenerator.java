package surveasy.global.common.util.toss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TossSecretKeyGenerator {
    private static final String KEY_TYPE = "Basic ";

    @Value("${toss.secretKey}")
    private String secretKey;

    public String generateSecretKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        return KEY_TYPE + new String(encodedBytes);
    }
}
