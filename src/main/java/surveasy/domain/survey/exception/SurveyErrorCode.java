package surveasy.domain.survey.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import surveasy.global.common.dto.ErrorReason;
import surveasy.global.error.BaseErrorCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum SurveyErrorCode implements BaseErrorCode {

    SURVEY_NOT_FOUND(NOT_FOUND, "SURVEY_404_1", "존재하지 않는 설문입니다."),

    SURVEY_CANNOT_EDIT(BAD_REQUEST, "SURVEY_400_1", "이미 응답 수집이 진행중이므로 수정이 불가능한 설문입니다."),

    SURVEY_CANNOT_DELETE(BAD_REQUEST, "SURVEY_400_2", "이미 응답 수집이 진행중이므로 삭제가 불가능한 설문입니다."),

    SURVEY_EXPIRED(BAD_REQUEST, "SURVEY_400_3", "응답 수집이 종료된 설문입니다."),

    SURVEY_NOT_RELEASED(BAD_REQUEST, "SURVEY_400_4", "응답 수집이 시작되지 않은 설문입니다.");

    private HttpStatus status;
    private String code;
    private String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
