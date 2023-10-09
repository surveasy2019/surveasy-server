package surveasy.domain.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseErrorCode implements BaseErrorCode {

    RESPONSE_UNAUTHORIZED(UNAUTHORIZED, "RESPONSE_401_1", "해당 응답 내역에 접근할 권한이 없습니다"),
    RESPONSE_NOT_FOUND(NOT_FOUND, "RESPONSE_404_1", "DB에 존재하지 않는 응답입니다."),
    RESPONSE_DUPLICATE_DATA(CONFLICT, "RESPONSE_409_1", "해당 설문은 이미 응답한 내역이 존재합니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
