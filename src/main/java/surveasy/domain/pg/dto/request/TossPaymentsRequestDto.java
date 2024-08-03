package surveasy.domain.pg.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;

@Builder(access = AccessLevel.PRIVATE)
public record TossPaymentsRequestDto(
        String paymentKey,
        String orderId,
        String amount
) {
    public static TossPaymentsRequestDto of(PaymentCreateRequestDto paymentCreateRequestDto) {
        return TossPaymentsRequestDto.builder()
                .paymentKey(paymentCreateRequestDto.paymentKey())
                .orderId(paymentCreateRequestDto.orderId())
                .amount(paymentCreateRequestDto.amount())
                .build();
    }
}
