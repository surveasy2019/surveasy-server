package surveasy.domain.coupon.exception;

import surveasy.global.error.BaseErrorException;

public class CouponNotFound extends BaseErrorException {

    public static final CouponNotFound EXCEPTION = new CouponNotFound();


    public CouponNotFound() {
        super(CouponBaseErrorCode.COUPON_NOT_FOUND);
    }
}
