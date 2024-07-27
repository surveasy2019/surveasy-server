package surveasy.domain.user.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InflowPath {
    EVERYTIME(1, "에브리타임"),
    KAKAO(2, "카카오톡"),
    GOOGLE(3, "구글 검색"),
    NAVER_SEARCH(4, "네이버 검색"),
    NAVER_BLOG(5, "네이버 블로그"),
    NAVER_IN(6, "네이버 지식인"),
    NAVER_CAFE(7, "네이버 카페"),
    INSTAGRAM(8, "인스타그램"),
    AD_EMAIL(9, "이메일 홍보"),
    AD_OFFLINE(10, "오프라인 홍보"),
    ACQUAINTANCE(11, "지인 추천"),
    ETC(12, "기타");

    private Integer code;
    private String desc;

    public static InflowPath ofFirebase(String firebaseInflowPath) {
        return switch (firebaseInflowPath) {
            case "everytime" -> EVERYTIME;
            case "kakaotalk" -> KAKAO;
            case "google" -> GOOGLE;
            case "naver" -> NAVER_SEARCH;
            case "blog" -> NAVER_BLOG;
            case "지식in" -> NAVER_IN;
            case "cafe" -> NAVER_CAFE;
            case "instagram" -> INSTAGRAM;
            case "email" -> AD_EMAIL;
            case "offline" -> AD_OFFLINE;
            case "acquaintance" -> ACQUAINTANCE;
            default -> ETC;
        };
    }
}
