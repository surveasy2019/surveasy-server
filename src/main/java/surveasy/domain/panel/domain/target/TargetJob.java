package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetJob {

    UNDERGRADUATE("대학생"),
    GRADUATE("대학원생");

    /*
    * <item>대학생</item>
        <item>대학원생</item>
        <item>사무직</item>
        <item>경영 관리직</item>
        <item>판매/서비스직</item>
        <item>자영업</item>
        <item>기능/생산직</item>
        <item>전문직</item>
        <item>농림어업</item>
        <item>전업주부</item>
        <item>무직</item>
        <item>기타</item>
    * */

    private final String value;

    TargetJob(String value) {
        this.value = value;
    }
}
