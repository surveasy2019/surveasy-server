package surveasy.domain.panel.domain.target;

import lombok.Getter;

@Getter
public enum TargetCity {

    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    SEJONG("세종특별자치시"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    CHUNGBUK("충청북도"),
    CHUNGNAM("충청남도"),
    JEONBUK("전라북도"),
    JEONNAM("전라남도"),
    GYEONGBUK("경상북도"),
    GYEONGNAM("경상남도"),
    JEJU("제주특별자치도");

    /*
    * <item>서울특별시</item>
        <item>부산광역시</item>
        <item>대구광역시</item>
        <item>인천광역시</item>
        <item>광주광역시</item>
        <item>대전광역시</item>
        <item>울산광역시</item>
        <item>세종특별자치시</item>
        <item>경기도</item>
        <item>강원도</item>
        <item>충청북도</item>
        <item>충청남도</item>
        <item>전라북도</item>
        <item>전라남도</item>
        <item>경상북도</item>
        <item>경상남도</item>
        <item>제주특별자치도</item>
    * */

    private final String value;

    TargetCity(String value) {
        this.value = value;
    }

}
