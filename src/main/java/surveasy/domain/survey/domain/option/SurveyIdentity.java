package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveyIdentity {

    MID_HIGH(0, "중/고등학생"),
    UNDERGRADUATE(1, "대학생"),
    GRADUATE(2, "대학원생"),
    GENERAL(3, "일반");

    private final int idx;

    private final String value;

    SurveyIdentity(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}