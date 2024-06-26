package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveyLanguage {

    KOR(1, "한국어"),
    ENG(2, "영어");

    private final int idx;

    private final String value;

    SurveyLanguage(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}