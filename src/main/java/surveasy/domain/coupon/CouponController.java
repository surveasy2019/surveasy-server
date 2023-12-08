package surveasy.domain.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.domain.coupon.dto.request.CouponUpdateDTO;
import surveasy.domain.coupon.dto.response.CouponIdResponse;
import surveasy.domain.coupon.dto.response.CouponListResponse;
import surveasy.domain.coupon.service.CouponService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Coupon")
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("")
    @Operation(summary = "어드민 쿠폰 목록")
    public CouponListResponse getCouponList() {
        return couponService.getCouponList();
    }

    @PostMapping("")
    @Operation(summary = "어드민 쿠폰 신규 생성")
    public CouponIdResponse createCoupon(@RequestBody @Valid CouponCreateDTO couponCreateDTO) {
        return couponService.createCoupon(couponCreateDTO);
    }

    @PatchMapping("/{couponId}")
    @Operation(summary = "어드민 쿠폰 업데이트")
    public CouponIdResponse updateCoupon(@PathVariable Long couponId, @RequestBody CouponUpdateDTO couponUpdateDTO) {
        return couponService.updateCoupon(couponId, couponUpdateDTO);
    }
}
