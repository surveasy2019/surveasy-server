package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetMilitary {

    YET("미필"),
    DONE("군필"),
    NONE("해당없음");

    private final String value;

    TargetMilitary(String value) {
        this.value = value;
    }
}
