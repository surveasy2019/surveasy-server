package surveasy.domain.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RefundType {
    ALL(1, "전액환불"),
    PERCENT(2, "부분환불");

    private final Integer code;
    private final String desc;
}
