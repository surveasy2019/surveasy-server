package surveasy.domain.coupon.dto.request;

import lombok.Getter;
import surveasy.domain.coupon.domain.CouponStatus;

@Getter
public class CouponUpdateDTO {

    private String code;
    private Integer discountPercent;
    private CouponStatus status;

}
