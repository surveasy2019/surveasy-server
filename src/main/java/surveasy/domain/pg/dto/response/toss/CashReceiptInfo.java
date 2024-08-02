package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record CashReceiptInfo(
        String type,
        String receiptKey,
        String issueNumber,
        String receiptUrl,
        Integer amount,
        Integer taxFreeAmount
) {
}
