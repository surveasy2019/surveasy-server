package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.panel.domain.option.PanelPlatform;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PanelSignUpDTO {

    String name;

    String email;

    String fcmToken;

    TargetGender gender;

    LocalDate birth;

    String accountOwner;

    AccountType accountType;

    String accountNumber;

    InflowPath inflowPath;

    String inflowPathEtc;

    String phoneNumber;

    PanelPlatform platform;

    Boolean pushOn;

    Boolean marketingAgree;


    @Builder
    public PanelSignUpDTO(String name, String email, String fcmToken,
                          TargetGender gender, LocalDate birth,
                          String accountOwner, AccountType accountType, String accountNumber,
                          InflowPath inflowPath, String inflowPathEtc,
                          String phoneNumber, PanelPlatform platform,
                          Boolean pushOn, Boolean marketingAgree) {
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = gender;
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.inflowPath = inflowPath;
        this.inflowPathEtc = inflowPathEtc;
        this.phoneNumber = phoneNumber;
        this.platform = platform;
        this.pushOn = pushOn;
        this.marketingAgree = marketingAgree;
    }
}
