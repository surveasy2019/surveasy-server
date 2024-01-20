package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.target.*;

import java.util.Map;

@Getter
public class PanelInfoFirstSurveyDAO {

    private static final Map<String, TargetCity> CITY_MAP = Map.ofEntries(
            Map.entry("서울특별시", TargetCity.SEOUL),
            Map.entry("부산광역시", TargetCity.BUSAN),
            Map.entry("대구광역시", TargetCity.DAEGU),
            Map.entry("인천광역시", TargetCity.INCHEON),
            Map.entry("광주광역시", TargetCity.GWANGJU),
            Map.entry("대전광역시", TargetCity.DAEJEON),
            Map.entry("울산광역시", TargetCity.ULSAN),
            Map.entry("세종특별자치시", TargetCity.SEJONG),
            Map.entry("경기도", TargetCity.GYEONGGI),
            Map.entry("강원도", TargetCity.GANGWON),
            Map.entry("충청북도", TargetCity.CHUNGBUK),
            Map.entry("충청남도", TargetCity.CHUNGNAM),
            Map.entry("전라북도", TargetCity.JEONBUK),
            Map.entry("전라남도", TargetCity.JEONNAM),
            Map.entry("경상북도", TargetCity.GYEONGBUK),
            Map.entry("경상남도", TargetCity.GYEONGNAM),
            Map.entry("제주특별자치도", TargetCity.JEJU)
    );

    private static final Map<String, TargetFamily> FAMILY_MAP = Map.of(
            "1인 가구", TargetFamily.PERSON_1,
            "1인가구", TargetFamily.PERSON_1,
            "2인 가구", TargetFamily.PERSON_2,
            "2인가구", TargetFamily.PERSON_2,
            "3인 가구", TargetFamily.PERSON_3,
            "3인가구", TargetFamily.PERSON_3,
            "4인 가구 이상", TargetFamily.PERSON_4,
            "4인가구이상", TargetFamily.PERSON_4
    );

    private static final Map<String, TargetHouseType> HOUSE_TYPE_MAP = Map.of(
            "아파트/주상복합", TargetHouseType.APT,
            "단독주택", TargetHouseType.DETACHED,
            "오피스텔/원룸", TargetHouseType.OFFICETEL,
            "다세대/빌라/연립", TargetHouseType.MULTIPLEX,
            "기숙사", TargetHouseType.DORMITORY,
            "기타", TargetHouseType.ETC
    );

    private static final Map<String, TargetJob> JOB_MAP = Map.ofEntries(
            Map.entry("중/고등학생", TargetJob.MIDDLE_HIGH),
            Map.entry("대학생", TargetJob.UNDERGRADUATE),
            Map.entry("대학원생", TargetJob.GRADUATE),
            Map.entry("사무직", TargetJob.OFFICE),
            Map.entry("경영 관리직", TargetJob.MANAGEMENT),
            Map.entry("판매/서비스직", TargetJob.SALES),
            Map.entry("자영업", TargetJob.BUSINESS),
            Map.entry("기능/생산직", TargetJob.PRODUCTION),
            Map.entry("전문직", TargetJob.SPECIALIZED),
            Map.entry("농림어업", TargetJob.FARMING_FISHING),
            Map.entry("전업주부", TargetJob.HOMEMAKER),
            Map.entry("무직", TargetJob.NONE),
            Map.entry("기타", TargetJob.ETC)
    );

    private static final Map<String, TargetMajor> MAJOR_MAP = Map.of(
            "사회계열", TargetMajor.SOCIAL,
            "공학계열", TargetMajor.ENGINEERING,
            "인문계열", TargetMajor.HUMAN,
            "자연계열", TargetMajor.NATURE,
            "예체능계열", TargetMajor.ART_PHYSICAL,
            "의약계열", TargetMajor.MEDICAL,
            "교육계열", TargetMajor.EDUCATION,
            "기타", TargetMajor.ETC
    );

    private static final Map<String, TargetMarriage> MARRIAGE_MAP = Map.of(
            "미혼", TargetMarriage.SINGLE,
            "기혼", TargetMarriage.MARRIED,
            "이혼", TargetMarriage.DIVORCE
    );

    private static final Map<String, TargetMilitary> MILITARY_MAP = Map.of(
            "군필", TargetMilitary.YET,
            "미필", TargetMilitary.DONE,
            "해당없음", TargetMilitary.NONE
    );

    private static final Map<String, TargetPet> PET_MAP = Map.of(
            "반려견", TargetPet.DOG,
            "반려묘", TargetPet.CAT,
            "기타", TargetPet.ETC,
            "없음", TargetPet.NONE
    );

    Boolean english;

    TargetCity city;

    String district;

    TargetFamily family;

    TargetHouseType houseType;

    TargetJob job;

    String university;

    TargetMajor major;

    TargetMarriage marriage;

    TargetMilitary military;

    TargetPet pet;

    @Builder
    public PanelInfoFirstSurveyDAO(Boolean english,
                                   String city,
                                   String district,
                                   String family,
                                   String houseType,
                                   String job,
                                   String university,
                                   String major,
                                   String marriage,
                                   String military,
                                   String pet) {
        this.english = english;
        this.city = CITY_MAP.get(city);
        this.district = district;
        this.family = FAMILY_MAP.get(family);
        this.houseType = HOUSE_TYPE_MAP.get(houseType);
        this.job = JOB_MAP.get(job);
        if(!university.equals("대학을 선택하세요")) this.university = university;
        this.major = MAJOR_MAP.get(major);
        this.marriage = MARRIAGE_MAP.get(marriage);
        this.military = MILITARY_MAP.get(military);
        this.pet = PET_MAP.get(pet);
    }
}
