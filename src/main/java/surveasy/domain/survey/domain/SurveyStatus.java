package surveasy.domain.survey.domain;

import lombok.Getter;

@Getter
public enum SurveyStatus {

    CREATED(0, "주문 완료"),
    WAITING(1, "검수 대기"),
    IN_PROGRESS(2, "응답 수집중"),
    DONE(3, "응답 수집 완료");

    private final int progress;
    private final String value;

    SurveyStatus(int progress, String value) {
        this.progress = progress;
        this.value = value;
    }
}
