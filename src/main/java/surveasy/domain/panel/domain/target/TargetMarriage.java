package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetMarriage {

    SINGLE("미혼"),
    MARRIED("기혼"),
    DIVORCE("이혼");

    private final String value;

    TargetMarriage(String value) {
        this.value = value;
    }

}
