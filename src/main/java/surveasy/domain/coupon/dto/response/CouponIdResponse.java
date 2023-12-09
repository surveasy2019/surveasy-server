package surveasy.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponIdResponse {

    private Long couponId;

    @Builder
    private CouponIdResponse(Long couponId) {
        this.couponId = couponId;
    }

    public static CouponIdResponse from(Long couponId) {
        return CouponIdResponse.builder()
                .couponId(couponId)
                .build();
    }
}
