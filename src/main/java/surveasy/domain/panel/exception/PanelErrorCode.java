package surveasy.domain.panel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum PanelErrorCode implements BaseErrorCode {

    PANEL_NOT_FOUND(NOT_FOUND, "PANEL_404_1", "존재하지 않는 패널입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
