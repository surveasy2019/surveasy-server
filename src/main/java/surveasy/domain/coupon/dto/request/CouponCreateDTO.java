package surveasy.domain.coupon.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CouponCreateDTO {

    @NotNull
    private String code;

    @NotNull
    private Integer discountPercent;
}
