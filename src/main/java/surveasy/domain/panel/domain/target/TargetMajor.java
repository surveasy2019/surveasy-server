package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetMajor {

    SOCIAL("사회계열"),
    ENGINEERING("공학계열");

    /*
    * <item>사회계열</item>
        <item>공학계열</item>
        <item>인문계열</item>
        <item>자연계열</item>
        <item>예체능계열</item>
        <item>의약계열</item>
        <item>교육계열</item>
        <item>기타</item>
    * */

    private final String value;

    TargetMajor(String value) {
        this.value = value;
    }
}
