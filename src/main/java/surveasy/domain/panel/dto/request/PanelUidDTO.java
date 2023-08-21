package surveasy.domain.panel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PanelUidDTO {

    @NotNull
    private String uid;
}
