package surveasy.domain.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_ALLOWED(BAD_REQUEST, "REVIEW_400_1", "아직 리뷰 작성이 불가능한 설문입니다"),

    REVIEW_NOT_FOUND(NOT_FOUND, "REVIEW_404_1", "존재하지 않는 리뷰입니다."),

    REVIEW_DUPLICATE_DATA(CONFLICT, "REVIEW_409_1", "작성이 완료된 리뷰가 존재합니다.");

    private HttpStatus status;
    private String code;
    private String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
