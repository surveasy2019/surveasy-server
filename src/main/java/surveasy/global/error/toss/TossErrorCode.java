package surveasy.global.error.toss;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class TossErrorCode implements BaseErrorCode {
    private ErrorReason errorReason;

    @Override
    public ErrorReason getErrorReason() {
        return this.errorReason;
    }

    public static TossErrorCode of(String code, String message) {
        HttpStatus status = getHttpStatus(code);
        return TossErrorCode.builder()
                .errorReason(ErrorReason.of(status.value(), code, message))
                .build();
    }

    private static HttpStatus getHttpStatus(String code) {
        if (code.equals(HttpStatus.BAD_REQUEST.series().name()))
            return HttpStatus.BAD_REQUEST;
        else if (code.equals(HttpStatus.UNAUTHORIZED.series().name()))
            return HttpStatus.UNAUTHORIZED;
        else if (code.equals(HttpStatus.FORBIDDEN.series().name()))
            return HttpStatus.FORBIDDEN;
        else if (code.equals(HttpStatus.NOT_FOUND.series().name()))
            return HttpStatus.BAD_REQUEST;
        else if (code.equals(HttpStatus.METHOD_NOT_ALLOWED.series().name()))
            return HttpStatus.METHOD_NOT_ALLOWED;
        else if (code.equals(HttpStatus.CONFLICT.series().name()))
            return HttpStatus.CONFLICT;
        else
            return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
