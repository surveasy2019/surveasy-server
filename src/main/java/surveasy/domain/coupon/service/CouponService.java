package surveasy.domain.coupon.service;

import org.springframework.stereotype.Service;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.request.CouponUpdateDTO;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;

@Service
public interface CouponService {
    CouponDiscountPercentResponse getCouponDiscountPercent(String code);

    CouponListResponse getCouponList();

    CouponIdResponse createCoupon(CouponCreateDTO couponCreateDTO);

    CouponIdResponse updateCoupon(Long couponId, CouponUpdateDTO couponUpdateDTO);

    void deleteCoupon(Long couponId);
}
