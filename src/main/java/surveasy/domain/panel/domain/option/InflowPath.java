package surveasy.domain.panel.domain.option;

import lombok.Getter;

@Getter
public enum InflowPath {

    KAKAO("카카오톡"),
    EVERYTIME("에브리타임"),
    INSTAGRAM("인스타그램"),
    ACQUAINTANCE("지인추천"),
    SEARCH_NAVER("네이버 검색"),
    SEARCH_GOOGLE("구글 검색"),
    OFFLINE("오프라인 홍보"),
    COMMUNITY("커뮤니티 홍보"),
    ETC("기타");

    private final String value;

    InflowPath(String value) {
        this.value = value;
    }
}
