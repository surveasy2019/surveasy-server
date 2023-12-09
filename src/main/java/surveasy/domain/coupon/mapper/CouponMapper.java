package surveasy.domain.coupon.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.coupon.domain.Coupon;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;

import java.util.List;

@Component
public class CouponMapper {

    public Coupon toEntity(CouponCreateDTO couponCreateDTO) {
        return Coupon.from(couponCreateDTO);
    }

    public CouponDiscountPercentResponse toCouponDiscountPercentResponse(Integer discountPercent) {
        return CouponDiscountPercentResponse.from(discountPercent);
    }

    public CouponListResponse toCouponListResponse(List<Coupon> couponList) {
        return CouponListResponse.from(couponList);
    }

    public CouponIdResponse toCouponIdResponse(Long couponId) {
        return CouponIdResponse.from(couponId);
    }
}
