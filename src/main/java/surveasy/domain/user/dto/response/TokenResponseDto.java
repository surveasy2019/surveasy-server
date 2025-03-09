package surveasy.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {
    public static TokenResponseDto of(String accessToken, String refreshToken) {
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
