package surveasy.domain.survey.domain.target;

import lombok.Getter;

@Getter
public enum TargetGender {

    ALL(1, "성별 무관"),
    MALE(2, "남"),
    FEMALE(3, "여");

    private final int idx;
    private final String value;

    TargetGender(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}