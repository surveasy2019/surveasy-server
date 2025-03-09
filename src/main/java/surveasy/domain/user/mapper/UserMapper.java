package surveasy.domain.user.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.user.dto.response.TokenResponseDto;

@Component
public class UserMapper {
    public TokenResponseDto toTokenResponseDto(String accessToken, String refreshToken) {
        return TokenResponseDto.of(accessToken, refreshToken);
    }
}
