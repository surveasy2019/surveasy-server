package surveasy.domain.coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.coupon.dto.request.CouponCreateDTO;
import surveasy.global.common.util.DateAndStringUtils;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private CouponStatus status = CouponStatus.VALID;

    @Builder
    private Coupon(String code, Integer discountPercent) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.createdAt = DateAndStringUtils.localDateToString(LocalDate.now());
    }

    public static Coupon from(CouponCreateDTO couponCreateDTO) {
        return Coupon.builder()
                .code(couponCreateDTO.getCode())
                .discountPercent(couponCreateDTO.getDiscountPercent())
                .build();
    }
}
