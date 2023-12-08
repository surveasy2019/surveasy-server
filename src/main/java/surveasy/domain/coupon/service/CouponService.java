package surveasy.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.request.CouponUpdateDTO;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;
import surveasy.domain.coupon.helper.CouponHelper;
import surveasy.domain.coupon.mapper.CouponMapper;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponHelper couponHelper;
    private final CouponMapper couponMapper;

    public CouponListResponse getCouponList() {
        return couponMapper.toCouponListResponse(couponHelper.getCouponList());
    }

    @Transactional
    public CouponIdResponse createCoupon(CouponCreateDTO couponCreateDTO) {
        return couponMapper.toCouponIdResponse(couponHelper.createCoupon(couponCreateDTO));
    }

    @Transactional
    public CouponIdResponse updateCoupon(Long couponId, CouponUpdateDTO couponUpdateDTO) {
        return couponMapper.toCouponIdResponse(couponHelper.updateCoupon(couponId, couponUpdateDTO));
    }
}
