package surveasy.domain.survey.domain.target;

import lombok.Getter;

@Getter
public enum TargetAge {

    ALL(1, "전연령"),
    AGE_20_24(2, "20-24세"),
    AGE_25_29(3, "25-29세"),
    AGE_30_34(4, "30-34세"),
    AGE_35_39(5, "35-39세"),
    AGE_40_44(6, "40-44세"),
    AGE_45_49(7, "45-49세"),
    AGE_50_59(8, "50대"),
    AGE_60_69(9, "60대");

    private final int idx;
    private final String value;

    TargetAge(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}
