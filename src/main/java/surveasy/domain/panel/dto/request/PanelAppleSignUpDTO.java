package surveasy.domain.panel.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;

@Getter
public class PanelAppleSignUpDTO {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private TargetGender gender;        // MALE or FEMALE

    @NotNull
    private LocalDate birth;            // yyyy-MM-dd

    @NotNull
    private String authId;

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
