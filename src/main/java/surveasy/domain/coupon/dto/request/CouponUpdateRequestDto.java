package surveasy.domain.coupon.dto.request;

import surveasy.domain.coupon.domain.CouponStatus;

public record CouponUpdateRequestDto(
        String code,
        Integer discountPercent,
        CouponStatus status
) {
}
