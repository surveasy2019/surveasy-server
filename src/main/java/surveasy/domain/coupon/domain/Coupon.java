package surveasy.domain.coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.coupon.dto.request.CouponCreateRequestDto;
import surveasy.domain.coupon.dto.request.CouponUpdateRequestDto;
import surveasy.global.common.util.DateAndStringUtils;

import java.time.LocalDate;

import static surveasy.global.common.util.EntityUpdateValueUtils.updateValue;

@Entity
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Integer discountPercent;

    @NotNull
    private String createdAt;

    @NotNull
    @Builder.Default
    private CouponStatus status = CouponStatus.VALID;

    public static Coupon createCoupon(CouponCreateRequestDto couponCreateRequestDto) {
        return Coupon.builder()
                .code(couponCreateRequestDto.code())
                .discountPercent(couponCreateRequestDto.discountPercent())
                .createdAt(DateAndStringUtils.localDateToString(LocalDate.now()))
                .build();
    }

    public void updateCoupon(CouponUpdateRequestDto couponUpdateRequestDto) {
        this.code = updateValue(this.code, couponUpdateRequestDto.code());
        this.discountPercent = updateValue(this.discountPercent, couponUpdateRequestDto.discountPercent());
        this.status = updateValue(this.status, couponUpdateRequestDto.status());
    }
}
