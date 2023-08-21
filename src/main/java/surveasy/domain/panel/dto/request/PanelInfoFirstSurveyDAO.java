package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class PanelInfoFirstSurveyDAO {

    private Map<String, Integer> fs_marriedMap = Map.of(
            "미혼", 0,
            "기혼", 1,
            "이혼", 2
    );

    private Map<String, Integer> fs_militaryMap = Map.of(
            "군필", 0,
            "미필", 1,
            "해당없음", 2
    );

    Boolean english;

    String city;

    String district;

    String family;

    String houseType;

    String job;

    String major;

    Integer married;

    Integer military;

    String pet;

    String university;

    @Builder
    public PanelInfoFirstSurveyDAO(Boolean english,
                                   String city,
                                   String district,
                                   String family,
                                   String houseType,
                                   String job,
                                   String major,
                                   String married,
                                   String military,
                                   String pet,
                                   String university) {
        this.english = english;
        this.city = city;
        this.district = district;
        this.family = family;
        this.houseType = houseType;
        this.job = job;
        this.major = major;
        this.married = fs_marriedMap.get(married);
        this.military = fs_militaryMap.get(military);
        this.pet = pet;
        this.university = university;
    }
}
