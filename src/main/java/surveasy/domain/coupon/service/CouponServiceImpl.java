package surveasy.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.coupon.domain.Coupon;
import surveasy.domain.coupon.dto.request.CouponCreateRequestDto;
import surveasy.domain.coupon.dto.request.CouponUpdateRequestDto;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;
import surveasy.domain.coupon.helper.CouponHelper;
import surveasy.domain.coupon.mapper.CouponMapper;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponHelper couponHelper;
    private final CouponMapper couponMapper;

    @Override
    public CouponDiscountPercentResponse getCouponDiscountPercent(String code) {
        Integer discountPercent = couponHelper.getCouponDiscountPercent(code);
        return couponMapper.toCouponDiscountPercentResponse(discountPercent);
    }

    @Override
    public CouponListResponse getCouponList() {
        List<Coupon> couponList = couponHelper.getCouponList();
        return couponMapper.toCouponListResponse(couponList);
    }

    @Override
    public CouponIdResponse createCoupon(CouponCreateRequestDto couponCreateRequestDto) {
        Coupon coupon = couponHelper.createCouponAndSave(couponCreateRequestDto);
        return couponMapper.toCouponIdResponse(coupon.getId());
    }

    @Override
    public CouponIdResponse updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) {
        Coupon coupon = couponHelper.findCouponByCouponId(couponId);
        coupon.updateCoupon(couponUpdateRequestDto);
        return couponMapper.toCouponIdResponse(coupon.getId());
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponHelper.deleteCoupon(couponId);
    }
}
