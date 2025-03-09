package surveasy.domain.pg.dto.response.toss;

import lombok.Builder;

@Builder
public record ReceiptInfo(
        String url
) {
}
