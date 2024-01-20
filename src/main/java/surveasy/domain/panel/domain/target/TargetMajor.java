package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetMajor {

    SOCIAL("사회계열"),
    ENGINEERING("공학계열"),
    HUMAN("인문계열"),
    NATURE("자연계열"),
    ART_PHYSICAL("예체능계열"),
    MEDICAL("의약계열"),
    EDUCATION("교육계열"),
    ETC("기타");

    private final String value;

    TargetMajor(String value) {
        this.value = value;
    }
}
