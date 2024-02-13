package surveasy.domain.panel.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.panel.domain.option.PanelPlatform;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PanelInfoDAO {

    private static final Map<String, TargetGender> GENDER_MAP = Map.of(
            "남", TargetGender.MALE,
            "여", TargetGender.FEMALE
    );
    private static final Map<String, AccountType> ACCOUNT_TYPE_MAP = Map.of(
            "국민", AccountType.KB,
            "하나", AccountType.HANA,
            "우리", AccountType.WOORI,
            "신한", AccountType.SHINHAN,
            "농협", AccountType.NH,
            "수협", AccountType.SH,
            "IBK 기업", AccountType.IBK,
            "새마을금고", AccountType.MG,
            "카카오뱅크", AccountType.KAKAO,
            "토스", AccountType.TOSS
    );
    private static final Map<String, InflowPath> INFLOW_PATH_MAP = Map.of(
            "카카오톡", InflowPath.KAKAO,
            "에브리타임", InflowPath.EVERYTIME,
            "인스타그램", InflowPath.INSTAGRAM,
            "지인 추천", InflowPath.ACQUAINTANCE,
            "기타", InflowPath.ETC
    );

    private static final Map<String, InflowPath> INFLOW_PATH_IDX_MAP = Map.of(
            "0", InflowPath.ETC,
            "1", InflowPath.KAKAO,
            "2", InflowPath.INSTAGRAM,
            "3", InflowPath.ACQUAINTANCE,
            "4", InflowPath.SEARCH_NAVER,
            "5", InflowPath.SEARCH_GOOGLE,
            "6", InflowPath.OFFLINE,
            "7", InflowPath.COMMUNITY
    );


    private String name;

    private String email;

    private String password;

    private String fcmToken;

    private TargetGender gender;

    private LocalDate birth;

    private String accountOwner;

    private AccountType accountType;

    private String accountNumber;

    private PanelStatus status;

    private InflowPath inflowPath;

    private String inflowPathEtc;

    private String phoneNumber;

    private PanelPlatform platform;

    private Boolean pushOn;

    private Boolean marketingAgree;


    private Integer rewardCurrent;

    private Integer rewardTotal;

    private LocalDate signedUpAt;

    private LocalDateTime lastParticipatedAt;


    @Builder
    public PanelInfoDAO(String name, String email, String password, String fcmToken,
                        String gender, LocalDate birth,
                        String accountOwner, String accountType, String accountNumber,
                        boolean didFirstSurvey, String inflowPath,
                        String phoneNumber, PanelPlatform platform,
                        Boolean pushOn,Boolean marketingAgree,
                        Integer rewardCurrent, Integer rewardTotal,
                        LocalDate signedUpAt, LocalDateTime lastParticipatedAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
        this.gender = GENDER_MAP.get(gender);
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = ACCOUNT_TYPE_MAP.get(accountType);
        this.accountNumber = accountNumber;

        if(didFirstSurvey) this.status = PanelStatus.FS_DONE;
        else this.status = PanelStatus.FS_YET;

        if(INFLOW_PATH_MAP.get(inflowPath) != null) this.inflowPath = INFLOW_PATH_MAP.get(inflowPath);
        else if(INFLOW_PATH_IDX_MAP.get(inflowPath) != null) this.inflowPath = INFLOW_PATH_IDX_MAP.get(inflowPath);
        else {
            this.inflowPath = InflowPath.ETC;
            this.inflowPathEtc = inflowPath;
        }

        this.phoneNumber = phoneNumber;
        this.platform = platform;
        this.pushOn = pushOn;
        this.marketingAgree = marketingAgree;
        this.rewardCurrent = rewardCurrent;
        this.rewardTotal = rewardTotal;
        this.lastParticipatedAt = lastParticipatedAt;
        this.signedUpAt = signedUpAt;
    }
}
