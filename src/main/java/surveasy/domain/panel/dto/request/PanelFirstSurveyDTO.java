package surveasy.domain.panel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import surveasy.domain.panel.domain.target.*;

@Getter
public class PanelFirstSurveyDTO {

    @NotNull
    private Boolean english;

    @NotNull
    private TargetCity city;

    @NotNull
    private String district;

    @NotNull
    private TargetFamily family;

    @NotNull
    private TargetHouseType houseType;

    @NotNull
    private TargetJob job;

    @NotNull
    private String university;

    @NotNull
    private TargetMajor major;

    @NotNull
    private TargetMarriage marriage;

    @NotNull
    private TargetMilitary military;

    @NotNull
    private TargetPet pet;
}
