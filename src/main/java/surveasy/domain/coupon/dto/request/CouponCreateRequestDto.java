package surveasy.domain.coupon.dto.request;

import jakarta.validation.constraints.NotNull;

public record CouponCreateRequestDto(
        @NotNull String code,
        @NotNull Integer discountPercent
) {
}
