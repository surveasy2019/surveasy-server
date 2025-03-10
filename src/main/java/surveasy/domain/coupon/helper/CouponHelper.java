package surveasy.domain.coupon.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.coupon.domain.Coupon;
import surveasy.domain.coupon.domain.CouponStatus;
import surveasy.domain.coupon.dto.request.CouponCreateRequestDto;
import surveasy.domain.coupon.exception.CouponDuplicateCode;
import surveasy.domain.coupon.exception.CouponNotFound;
import surveasy.domain.coupon.exception.InvalidCoupon;
import surveasy.domain.coupon.repository.CouponRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponHelper {
    private final CouponRepository couponRepository;

    public Coupon findCouponByCouponId(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> CouponNotFound.EXCEPTION);
    }

    private boolean checkExistingCode(String code) {
        return couponRepository.findByCode(code).isPresent();
    }

    public Integer getCouponDiscountPercent(String code) {
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> CouponNotFound.EXCEPTION);
        if(coupon.getStatus().equals(CouponStatus.INVALID)) throw InvalidCoupon.EXCEPTION;
        return coupon.getDiscountPercent();
    }

    public List<Coupon> getCouponList() {
        return couponRepository.findAll();
    }

    public Coupon createCouponAndSave(CouponCreateRequestDto couponCreateRequestDto) {
        if(checkExistingCode(couponCreateRequestDto.code())) throw CouponDuplicateCode.EXCEPTION;
        Coupon coupon = Coupon.createCoupon(couponCreateRequestDto);
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> CouponNotFound.EXCEPTION);
        couponRepository.delete(coupon);
    }
}
