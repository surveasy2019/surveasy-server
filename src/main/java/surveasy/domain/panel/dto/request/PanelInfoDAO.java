package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PanelInfoDAO {

    private Map<String, Integer> genderMap = Map.of("남", 0, "여", 1);
    private Map<String, Integer> platformMap = Map.of("ios", 0, "android", 1);

    String uid;

    String name;

    String email;

    String fcmToken;

    Integer gender;

    Date birth;

    String accountOwner;

    String accountType;

    String accountNumber;

    Boolean didFirstSurvey;

    String inflowPath;

    Date lastParticipatedAt;

    Boolean marketingAgree;

    String phoneNumber;

    Integer platform;

    Boolean pushOn;

    Date signedUpAt;

    Integer rewardCurrent;

    Integer rewardTotal;

    Boolean snsAuth;

    String snsUid;


    @Builder
    public PanelInfoDAO(String uid, String name, String email,
                        String fcmToken, String gender, Date birth,
                        String accountOwner, String accountType, String accountNumber,
                        Boolean didFirstSurvey,
                        String inflowPath, Date lastParticipatedAt,
                        Boolean marketingAgree, String phoneNumber,
                        Integer platform, Boolean pushOn,
                        Date signedUpAt, Integer rewardCurrent,
                        Integer rewardTotal, Boolean snsAuth,
                        String snsUid
    ) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = genderMap.get(gender);
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.didFirstSurvey = didFirstSurvey;
        this.inflowPath = inflowPath;
        this.lastParticipatedAt = lastParticipatedAt;
        this.marketingAgree = marketingAgree;
        this.phoneNumber = phoneNumber;
        this.platform = platform;
        this.pushOn = pushOn;
        this.signedUpAt = signedUpAt;
        this.rewardCurrent = rewardCurrent;
        this.rewardTotal = rewardTotal;
        this.snsAuth = snsAuth;
        this.snsUid = snsUid;
    }
}
