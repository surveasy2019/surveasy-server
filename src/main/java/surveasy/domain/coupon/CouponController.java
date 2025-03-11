package surveasy.domain.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.coupon.dto.request.CouponCreateRequestDto;
import surveasy.domain.coupon.dto.request.CouponUpdateRequestDto;
import surveasy.domain.coupon.dto.response.CouponDiscountPercentResponse;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;
import surveasy.domain.coupon.service.CouponService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Coupon")
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{code}")
    @Operation(summary = "쿠폰 유효성 확인 & 할인율 받아오기")
    public CouponDiscountPercentResponse getCouponDiscountPercent(@PathVariable String code) {
        return couponService.getCouponDiscountPercent(code);
    }

    @GetMapping
    @Operation(summary = "어드민 쿠폰 목록")
    public CouponListResponse getCouponList() {
        return couponService.getCouponList();
    }

    @PostMapping
    @Operation(summary = "어드민 쿠폰 신규 생성")
    public CouponIdResponse createCoupon(@RequestBody @Valid CouponCreateRequestDto couponCreateRequestDto) {
        return couponService.createCoupon(couponCreateRequestDto);
    }

    @PatchMapping("/{couponId}")
    @Operation(summary = "어드민 쿠폰 업데이트")
    public CouponIdResponse updateCoupon(@PathVariable Long couponId, @RequestBody CouponUpdateRequestDto couponUpdateRequestDto) {
        return couponService.updateCoupon(couponId, couponUpdateRequestDto);
    }

    @DeleteMapping("/{couponId}")
    @Operation(summary = "어드민 쿠폰 삭제")
    public void deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
    }
}
