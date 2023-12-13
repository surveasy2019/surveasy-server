package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter

public enum TargetFamily {

    PERSON_1("1인 가구"),
    PERSON_2("2인 가구"),
    PERSON_3("3인 가구"),
    PERSON_4("4인 가구 이상");

    private final String value;

    TargetFamily(String value) {
        this.value = value;
    }
}
