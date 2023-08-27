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

    PANEL_NOT_FOUND_FB(NOT_FOUND, "PANEL_404_1", "FB에 존재하지 않는 패널입니다."),
    PANEL_NOT_FOUND_DB(NOT_FOUND, "PANEL_404_2", "DB에 존제하지 않는 패널입니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
