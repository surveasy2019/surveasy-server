package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetHouseType {

    APT("아파트/주상복합"),
    DETACHED("단독주택"),
    OFFICETEL("오피스텔/원룸"),
    MULTIPLEX("다세대/빌라/연립"),
    DORMITORY("기숙사"),
    ETC("기타");

    private final String value;

    TargetHouseType(String value) {
        this.value = value;
    }

}
