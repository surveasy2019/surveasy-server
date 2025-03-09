package surveasy.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    CONFLICT_USER(CONFLICT, "USER_404_1", "이미 가입된 이메일입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "USER_401_1", "권한이 없는 사용자입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
