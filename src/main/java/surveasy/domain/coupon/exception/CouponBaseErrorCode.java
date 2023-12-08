package surveasy.domain.coupon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CouponBaseErrorCode implements BaseErrorCode {

    COUPON_DUPLICATE_CODE(CONFLICT, "COUPON_409_1", "이미 존재하는 쿠폰 코드입니다"),
    COUPON_NOT_FOUND(NOT_FOUND, "COUPON_404_1", "존재하지 않는 쿠폰입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
