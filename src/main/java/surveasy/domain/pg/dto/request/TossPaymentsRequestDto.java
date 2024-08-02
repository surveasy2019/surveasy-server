package surveasy.domain.pg.dto.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TossPaymentsRequestDto(
        String paymentKey,
        String orderId,
        String amount
) {
}
