package surveasy.domain.panel.domain.option;

import lombok.Getter;

@Getter
public enum InflowPath {

    KAKAO("카카오톡"),
    EVERYTIME("에브리타임"),
    INSTAGRAM("인스타그램"),
    ACQUAINTANCE("지인추천"),
    ETC("기타");

    private final String value;

    InflowPath(String value) {
        this.value = value;
    }
}
