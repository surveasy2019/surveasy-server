package surveasy.domain.panel.dto.request;

import jakarta.annotation.Nullable;
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

    @Nullable
    private TargetMajor major;

    @NotNull
    private TargetPet pet;
}
