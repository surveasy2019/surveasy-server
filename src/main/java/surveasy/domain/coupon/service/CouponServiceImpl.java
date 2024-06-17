package surveasy.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.request.CouponUpdateDTO;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;
import surveasy.domain.coupon.helper.CouponHelper;
import surveasy.domain.coupon.mapper.CouponMapper;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponHelper couponHelper;
    private final CouponMapper couponMapper;

    @Override
    public CouponDiscountPercentResponse getCouponDiscountPercent(String code) {
        return couponMapper.toCouponDiscountPercentResponse(couponHelper.getCouponDiscountPercent(code));
    }

    @Override
    public CouponListResponse getCouponList() {
        return couponMapper.toCouponListResponse(couponHelper.getCouponList());
    }

    @Override
    public CouponIdResponse createCoupon(CouponCreateDTO couponCreateDTO) {
        return couponMapper.toCouponIdResponse(couponHelper.createCoupon(couponCreateDTO));
    }

    @Override
    public CouponIdResponse updateCoupon(Long couponId, CouponUpdateDTO couponUpdateDTO) {
        return couponMapper.toCouponIdResponse(couponHelper.updateCoupon(couponId, couponUpdateDTO));
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponHelper.deleteCoupon(couponId);
    }
}
