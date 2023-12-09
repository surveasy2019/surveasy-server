package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveyIdentity {

    MID_HIGH(1, "중/고등학생"),
    UNDERGRADUATE(2, "대학생"),
    GRADUATE(3, "대학원생"),
    GENERAL(4, "일반");

    private final int idx;

    private final String value;

    SurveyIdentity(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}