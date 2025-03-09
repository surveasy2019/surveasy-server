package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record FailureInfo(
        String code,
        String message
) {
}
