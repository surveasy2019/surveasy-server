package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record GiftCertificateInfo(
        String approveNo,
        String settlementStatus
) {
}
