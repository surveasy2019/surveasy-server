package surveasy.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponDiscountPercentResponse {

    private Integer discountPercent;

    @Builder
    private CouponDiscountPercentResponse(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public static CouponDiscountPercentResponse from(Integer discountPercent) {
        return CouponDiscountPercentResponse.builder()
                .discountPercent(discountPercent)
                .build();
    }
}
