package surveasy.domain.coupon.exception;

import surveasy.global.error.BaseErrorException;

public class CouponDuplicateCode extends BaseErrorException {

    public static final CouponDuplicateCode EXCEPTION = new CouponDuplicateCode();

    public CouponDuplicateCode() {
        super(CouponBaseErrorCode.COUPON_DUPLICATE_CODE);
    }
}
