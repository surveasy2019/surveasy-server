package surveasy.domain.panel.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.panel.domain.option.PanelPlatform;

@Getter
public class PanelSignUpDTO {

    @NotNull
    private PanelPlatform platform;

    @NotNull
    private String accountOwner;

    @NotNull
    private AccountType accountType;

    @NotNull
    private String accountNumber;

    @NotNull
    private InflowPath inflowPath;

    @Nullable
    private String inflowPathEtc;

    @NotNull
    private Boolean pushOn;

    @NotNull
    private Boolean marketingAgree;

}
