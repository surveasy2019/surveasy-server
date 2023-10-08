package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PanelSignUpDTO {

    private Map<String, Integer> genderMap = Map.of("남", 0, "여", 1);
    private Map<String, Integer> platformMap = Map.of("ios", 0, "android", 1);

    String name;

    String email;

    String fcmToken;

    Integer gender;

    Date birth;

    String accountOwner;

    String accountType;

    String accountNumber;

    String inflowPath;

    Boolean marketingAgree;

    String phoneNumber;

    Integer platform;

    Boolean pushOn;

    Date signedUpAt;

    Integer rewardCurrent;

    Integer rewardTotal;


    @Builder
    public PanelSignUpDTO(String name, String email,
                        String fcmToken, String gender, Date birth,
                        String accountOwner, String accountType, String accountNumber,
                        String inflowPath, Boolean marketingAgree, String phoneNumber,
                        Integer platform, Boolean pushOn
    ) {
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = genderMap.get(gender);
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.inflowPath = inflowPath;
        this.marketingAgree = marketingAgree;
        this.phoneNumber = phoneNumber;
        this.platform = platform;
        this.pushOn = pushOn;
        this.signedUpAt = new Date();
        this.rewardCurrent = 0;
        this.rewardTotal = 0;
    }
}
