package surveasy.domain.survey.domain.target;

import lombok.Getter;

@Getter
public enum TargetGender {

    ALL(0, "성별 무관"),
    MALE(1, "남"),
    FEMALE(2, "여");

    private final int idx;
    private final String value;

    TargetGender(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}