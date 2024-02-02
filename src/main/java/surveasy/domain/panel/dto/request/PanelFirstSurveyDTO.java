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
    private TargetFamily family;

    @NotNull
    private TargetJob job;

    @NotNull
    private TargetMajor major;

    @NotNull
    private TargetPet pet;
}
