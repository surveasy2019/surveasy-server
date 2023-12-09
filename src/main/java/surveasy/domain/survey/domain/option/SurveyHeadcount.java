package surveasy.domain.survey.domain.option;

import lombok.Getter;

@Getter
public enum SurveyHeadcount {

    HEAD_30(1, 30),
    HEAD_40(2, 40),
    HEAD_50(3, 50),
    HEAD_60(4, 60),
    HEAD_70(5, 70),
    HEAD_80(6, 80),
    HEAD_90(7, 90),
    HEAD_100(8, 100),
    HEAD_120(9, 120),
    HEAD_140(10, 140),
    HEAD_160(11, 160),
    HEAD_180(12, 180),
    HEAD_200(13, 200);

    private final int idx;

    private final int value;

    SurveyHeadcount(int idx, int value) {
        this.idx = idx;
        this.value = value;
    }
}
