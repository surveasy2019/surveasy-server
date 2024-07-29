package surveasy.domain.panel.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import surveasy.global.common.enm.AuthType;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void setRefreshToken(AuthType authType, Long id, String token, int expirationTime) {
        switch (authType) {
            case USER:
                redisTemplate.opsForValue().set("user_" + id.toString(), token, expirationTime, TimeUnit.SECONDS);
            case PANEL:
                redisTemplate.opsForValue().set(id.toString(), token, expirationTime, TimeUnit.SECONDS);
        }
    }

    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
