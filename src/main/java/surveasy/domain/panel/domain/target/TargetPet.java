package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetPet {

    DOG("반려견"),
    CAT("반려묘"),
    ETC("기타"),
    NONE("없음");

    private final String value;

    TargetPet(String value) {
        this.value = value;
    }
}
