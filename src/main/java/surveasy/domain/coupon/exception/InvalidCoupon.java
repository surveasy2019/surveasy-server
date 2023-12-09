package surveasy.domain.coupon.exception;

import surveasy.global.error.BaseErrorException;

public class InvalidCoupon extends BaseErrorException {

    public static final InvalidCoupon EXCEPTION = new InvalidCoupon();


    public InvalidCoupon() {
        super(CouponBaseErrorCode.COUPON_INVALID);
    }
}
