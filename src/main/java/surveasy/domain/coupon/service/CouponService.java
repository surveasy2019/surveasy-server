package surveasy.domain.coupon.service;

import org.springframework.stereotype.Service;
import surveasy.domain.coupon.dto.request.CouponCreateRequestDto;
import surveasy.domain.coupon.dto.request.CouponUpdateRequestDto;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;

@Service
public interface CouponService {
    CouponDiscountPercentResponse getCouponDiscountPercent(String code);

    CouponListResponse getCouponList();

    CouponIdResponse createCoupon(CouponCreateRequestDto couponCreateRequestDto);

    CouponIdResponse updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto);

    void deleteCoupon(Long couponId);
}
