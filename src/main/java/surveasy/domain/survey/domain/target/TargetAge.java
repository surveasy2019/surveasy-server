package surveasy.domain.survey.domain.target;

import lombok.Getter;

@Getter
public enum TargetAge {

    ALL(0, "전연령"),
    AGE_20_24(1, "20-24세"),
    AGE_25_29(2, "25-29세"),
    AGE_30_34(3, "30-34세"),
    AGE_35_39(4, "35-39세"),
    AGE_40_44(5, "40-44세"),
    AGE_45_49(6, "45-49세"),
    AGE_50_59(7, "50대"),
    AGE_60_69(8, "60대");

    private final int idx;
    private final String value;

    TargetAge(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}
