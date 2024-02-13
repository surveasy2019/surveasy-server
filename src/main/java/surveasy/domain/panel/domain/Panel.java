package surveasy.domain.panel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import surveasy.domain.panel.domain.option.*;
import surveasy.domain.panel.domain.target.*;
import surveasy.domain.panel.dto.request.OAuth2UserInfo;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE panel SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Panel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Size(max = 128)
    @JsonIgnore
    private String password;

    @NotNull
    private String fcmToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TargetGender gender;

    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    private String accountOwner;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InflowPath inflowPath;

    @Nullable
    private String inflowPathEtc;

    @NotNull
    private String phoneNumber;

    @NotNull
    private PanelPlatform platform;

    @NotNull
    private Boolean pushOn;

    @NotNull
    private Boolean marketingAgree;


    // Default
    @NotNull
    private String role;

    @Nullable
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PanelStatus status;

    @NotNull
    private Integer rewardCurrent;

    @NotNull
    private Integer rewardTotal;

    @NotNull
    private Integer rewardTemp = 0;

    @Nullable
    private LocalDate signedUpAt;

    @Nullable
    private LocalDateTime lastParticipatedAt;

    @Nullable
    @Column(length = 10)
    private LocalDate deletedAt;


    // First Survey
    @Nullable
    private Boolean english;

    @Nullable
    @Enumerated(EnumType.STRING)
    private TargetCity city;

    @Nullable
    @Enumerated(EnumType.STRING)
    private TargetFamily family;

    @Nullable
    @Enumerated(EnumType.STRING)
    private TargetJob job;

    @Nullable
    @Enumerated(EnumType.STRING)
    private TargetMajor major;

    @Nullable
    @Enumerated(EnumType.STRING)
    private TargetPet pet;


    @Builder
    private Panel(String name, String email, String password, String fcmToken, TargetGender gender,
                 LocalDate birth, String accountOwner, AccountType accountType, String accountNumber,
                 PanelStatus status, InflowPath inflowPath, String inflowPathEtc, String phoneNumber,
                 PanelPlatform platform, Boolean pushOn, Boolean marketingAgree,
                 Integer rewardCurrent, Integer rewardTotal, LocalDate signedUpAt, LocalDateTime lastParticipatedAt,
                  Boolean english, TargetCity city, TargetFamily family, TargetJob job,
                  TargetMajor major, TargetPet pet, String role, AuthProvider authProvider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
        this.gender = gender;
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.status = status;
        this.inflowPath = inflowPath;
        this.inflowPathEtc = inflowPathEtc;
        this.phoneNumber = phoneNumber;
        this.platform = platform;
        this.pushOn = pushOn;
        this.marketingAgree = marketingAgree;

        this.rewardCurrent = rewardCurrent;
        this.rewardTotal = rewardTotal;
        this.signedUpAt = signedUpAt;
        this.lastParticipatedAt = lastParticipatedAt;

        /* First Survey */
        this.english = english;
        this.city = city;
        this.family = family;
        this.job = job;
        this.major = major;
        this.pet = pet;

        this.role = role;
        this.authProvider = authProvider;
    }


    public static Panel ofExisting(PanelInfoDAO panelInfoDAO, PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO) {
        return Panel.builder()
                .name(panelInfoDAO.getName())
                .email(panelInfoDAO.getEmail())
                .password(panelInfoDAO.getPassword())
                .fcmToken(panelInfoDAO.getFcmToken())
                .gender(panelInfoDAO.getGender())
                .birth(panelInfoDAO.getBirth())
                .accountOwner(panelInfoDAO.getAccountOwner())
                .accountType(panelInfoDAO.getAccountType())
                .accountNumber(panelInfoDAO.getAccountNumber())
                .status(panelInfoDAO.getStatus())
                .inflowPath(panelInfoDAO.getInflowPath())
                .inflowPathEtc(panelInfoDAO.getInflowPathEtc())
                .phoneNumber(panelInfoDAO.getPhoneNumber())
                .platform(panelInfoDAO.getPlatform())
                .pushOn(panelInfoDAO.getPushOn())
                .marketingAgree(panelInfoDAO.getMarketingAgree())

                .rewardCurrent(panelInfoDAO.getRewardCurrent())
                .rewardTotal(panelInfoDAO.getRewardTotal())
                .signedUpAt(panelInfoDAO.getSignedUpAt())
                .lastParticipatedAt(panelInfoDAO.getLastParticipatedAt())

                .english(panelInfoFirstSurveyDAO.getEnglish())
                .city(panelInfoFirstSurveyDAO.getCity())
                .family(panelInfoFirstSurveyDAO.getFamily())
                .job(panelInfoFirstSurveyDAO.getJob())
                .major(panelInfoFirstSurveyDAO.getMajor())
                .pet(panelInfoFirstSurveyDAO.getPet())
                .role("ROLE_USER")
                .build();
    }

    public static Panel ofOAuth2(OAuth2UserInfo oAuth2UserInfo) {
        return Panel.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .password("")
                .fcmToken("")
                .gender(oAuth2UserInfo.getGender())
                .birth(oAuth2UserInfo.getBirth())
                .accountOwner("")
                .accountType(AccountType.KB)
                .accountNumber("")
                .status(PanelStatus.INFO_YET)
                .inflowPath(InflowPath.ETC)
                .phoneNumber(oAuth2UserInfo.getPhoneNumber())
                .platform(PanelPlatform.ANDROID)
                .pushOn(false)
                .marketingAgree(false)

                .rewardCurrent(0)
                .rewardTotal(0)
                .signedUpAt(LocalDate.now())

                .english(null)
                .city(null)
                .family(null)
                .job(null)
                .major(null)
                .pet(null)
                .role("ROLE_ANONYMOUS")
                .authProvider(oAuth2UserInfo.getAuthProvider())
                .build();

    }

    public Panel updateFrom(OAuth2UserInfo oAuth2UserInfo) {
        this.name = oAuth2UserInfo.getName();
        this.email = oAuth2UserInfo.getEmail();
        this.phoneNumber = oAuth2UserInfo.getPhoneNumber();
        this.gender = oAuth2UserInfo.getGender();
        this.birth = oAuth2UserInfo.getBirth();
        return this;
    }
}


