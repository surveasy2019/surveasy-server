package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveyHeadcount {

    HEAD_30(0, 30),
    HEAD_40(1, 40),
    HEAD_50(2, 50),
    HEAD_60(3, 60),
    HEAD_70(4, 70),
    HEAD_80(5, 80),
    HEAD_90(6, 90),
    HEAD_100(7, 100),
    HEAD_120(8, 120),
    HEAD_140(9, 140),
    HEAD_160(10, 160),
    HEAD_180(11, 180),
    HEAD_200(12, 200);

    private final int idx;

    private final int value;

    SurveyHeadcount(int idx, int value) {
        this.idx = idx;
        this.value = value;
    }
}
