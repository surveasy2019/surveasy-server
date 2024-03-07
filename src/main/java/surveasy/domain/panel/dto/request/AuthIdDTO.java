package surveasy.domain.panel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthIdDTO {

    @NotNull
    private String authId;

}
