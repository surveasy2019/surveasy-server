package surveasy.domain.pg.dto.response.toss;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record CancelsInfo(
        Integer cancelAmount,
        String cancelReason,
        Integer taxFreeAmount,
        Integer taxExemptionAmount,
        Integer refundableAmount,
        Integer easyPayDiscountAmount,
        String canceledAt,
        String transactionKey,
        @Nullable String receiptKey,
        String cancelStatus,
        @Nullable String cancelRequestId
) {
}
