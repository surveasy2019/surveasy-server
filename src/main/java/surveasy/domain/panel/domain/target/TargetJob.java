package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetJob {

    MID_HIGH("중/고등학생"),
    UNDERGRADUATE("대학생"),
    GRADUATE("대학원생"),
    OFFICE("사무직"),
    MANAGEMENT("경영 관리직"),
    SALES("판매/서비스직"),
    BUSINESS("자영업"),
    PRODUCTION("기능/생산직"),
    SPECIALIZED("전문직"),
    FARMING_FISHING("농림어업"),
    HOMEMAKER("전업주부"),
    NONE("무직"),
    ETC("기타");

    private final String value;

    TargetJob(String value) {
        this.value = value;
    }
}
