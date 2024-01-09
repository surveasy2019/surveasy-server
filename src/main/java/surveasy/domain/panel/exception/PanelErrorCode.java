package surveasy.domain.panel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum PanelErrorCode implements BaseErrorCode {

    PANEL_NOT_FOUND_FB(NOT_FOUND, "PANEL_404_1", "FB에 존재하지 않는 패널입니다."),
    PANEL_NOT_FOUND_DB(NOT_FOUND, "PANEL_404_2", "DB에 존제하지 않는 패널입니다"),
    REFRESH_NOT_FOUND(NOT_FOUND, "PANEL_404_3", "존재하지 않거나 만료된 리프레쉬 토큰입니다"),
    NOT_REFRESH_TOKEN(BAD_REQUEST, "PANEL_400_1", "리프레쉬 토큰이 아닙니다"),
    PANEL_DUPLICATE_DATA(CONFLICT, "PANEL_409_1", "이미 DB에 존재하는 패널입니다");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
