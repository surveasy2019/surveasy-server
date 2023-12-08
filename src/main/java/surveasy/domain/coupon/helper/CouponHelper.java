package surveasy.domain.coupon.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.coupon.domain.Coupon;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.request.CouponUpdateDTO;
import surveasy.domain.coupon.exception.CouponDuplicateCode;
import surveasy.domain.coupon.exception.CouponNotFound;
import surveasy.domain.coupon.mapper.CouponMapper;
import surveasy.domain.coupon.repository.CouponRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponHelper {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    private boolean checkExistingCode(String code) {
        return couponRepository.findByCode(code) != null;
    }

    public List<Coupon> getCouponList() {
        return couponRepository.findAll();
    }

    public Long createCoupon(CouponCreateDTO couponCreateDTO) {

        // 중복된 코드 여부 확인
        if(checkExistingCode(couponCreateDTO.getCode())) {
            throw CouponDuplicateCode.EXCEPTION;
        }

        Coupon newCoupon = couponMapper.toEntity(couponCreateDTO);
        Coupon savedCoupon = couponRepository.save(newCoupon);
        return savedCoupon.getId();
    }

    public Long updateCoupon(Long couponId, CouponUpdateDTO couponUpdateDTO) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> CouponNotFound.EXCEPTION);

        if(couponUpdateDTO.getCode() != null) {
            if(checkExistingCode(couponUpdateDTO.getCode())) {
                throw CouponDuplicateCode.EXCEPTION;
            }
            coupon.setCode(couponUpdateDTO.getCode());
        }

        if(couponUpdateDTO.getDiscountPercent() != null) {
            coupon.setDiscountPercent(couponUpdateDTO.getDiscountPercent());
        }

        if(couponUpdateDTO.getStatus() != null) {
            coupon.setStatus(couponUpdateDTO.getStatus());
        }

        return coupon.getId();
    }
}
