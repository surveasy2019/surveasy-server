package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.target.*;

import java.util.Map;

@Getter
public class PanelInfoFirstSurveyDAO {

    private static final Map<String, TargetCity> CITY_MAP = Map.of(
            "서울특별시", TargetCity.SEOUL,
            "경기도", TargetCity.GYEONG_GI
    );

    private static final Map<String, TargetFamily> FAMILY_MAP = Map.of(
            "1인 가구", TargetFamily.PERSON_1,
            "2인 가구", TargetFamily.PERSON_2,
            "3인 가구", TargetFamily.PERSON_3,
            "4인 가구 이상", TargetFamily.PERSON_4
    );

    private static final Map<String, TargetHouseType> HOUSE_TYPE_MAP = Map.of(
            "아파트/주상복합", TargetHouseType.APT,
            "단독주택", TargetHouseType.DETACHED,
            "오피스텔/원룸", TargetHouseType.OFFICETEL,
            "다세대/빌라/연립", TargetHouseType.MULTIPLEX,
            "기숙사", TargetHouseType.DORMITORY,
            "기타", TargetHouseType.ETC
    );

    private static final Map<String, TargetJob> JOB_MAP = Map.of(
            "대학생", TargetJob.UNDERGRADUATE,
            "대학원생", TargetJob.GRADUATE
    );

    private static final Map<String, TargetMajor> MAJOR_MAP = Map.of(
            "사회계열", TargetMajor.SOCIAL,
            "공학계열", TargetMajor.ENGINEERING
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
        this.university = university;
        this.major = MAJOR_MAP.get(major);
        this.marriage = MARRIAGE_MAP.get(marriage);
        this.military = MILITARY_MAP.get(military);
        this.pet = PET_MAP.get(pet);
    }
}
