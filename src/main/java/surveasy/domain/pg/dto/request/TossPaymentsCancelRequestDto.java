package surveasy.domain.pg.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.payment.domain.RefundType;

@Builder(access = AccessLevel.PRIVATE)
public record TossPaymentsCancelRequestDto(
        String cancelReason,
        Integer cancelAmount
) {
    public static TossPaymentsCancelRequestDto of(RefundType refundType, String cancelReason, Integer cancelAmount) {
        if(refundType.equals(RefundType.ALL)) {
            return TossPaymentsCancelRequestDto.builder()
                    .cancelReason(cancelReason)
                    .build();
        }
        return TossPaymentsCancelRequestDto.builder()
                .cancelReason(cancelReason)
                .cancelAmount(cancelAmount)
                .build();
    }
}
