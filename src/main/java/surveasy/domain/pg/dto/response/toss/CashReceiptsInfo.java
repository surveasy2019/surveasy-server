package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record CashReceiptsInfo(
        String receiptKey,
        String orderId,
        String orderName,
        String type,
        String issueNumber,
        String receiptUrl,
        String businessNumber,
        String transactionType,
        Integer amount,
        Integer taxFreeAmount,
        String issueStatus,
        FailureInfo failure,
        String customerIdentityNumber,
        String requestedAt
) {
}
