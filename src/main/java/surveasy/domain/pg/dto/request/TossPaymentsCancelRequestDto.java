package surveasy.domain.pg.dto.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TossPaymentsCancelRequestDto(
        String cancelReason,
        Integer cancelAmount
) {
}
