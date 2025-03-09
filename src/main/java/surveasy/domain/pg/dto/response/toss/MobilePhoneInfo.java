package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record MobilePhoneInfo(
        String customerMobilePhone,
        String settlementStatus,
        String receiptUrl
) {
}
