package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveySpendTime {

    TIME_0(1, "1분 이내"),
    TIME_1_3(2, "1-3분"),
    TIME_4_6(3, "4-6분"),
    TIME_7_10(4, "7-10분"),
    TIME_11_15(5, "11-15분"),
    TIME_16_20(6, "16-20분");

    private final int idx;

    private final String value;

    SurveySpendTime(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }
}