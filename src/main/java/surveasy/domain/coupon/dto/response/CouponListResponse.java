package surveasy.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.coupon.domain.Coupon;

import java.util.List;

@Getter
public class CouponListResponse {

    private List<Coupon> couponList;

    @Builder
    private CouponListResponse(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public static CouponListResponse from(List<Coupon> couponList) {
        return CouponListResponse.builder()
                .couponList(couponList)
                .build();
    }
}
