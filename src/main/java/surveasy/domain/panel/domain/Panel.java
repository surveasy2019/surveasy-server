package surveasy.domain.panel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.panel.domain.option.PanelPlatform;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.panel.domain.target.*;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;
import surveasy.domain.panel.oauth2.AuthProvider;
import surveasy.domain.panel.oauth2.user.OAuth2UserInfo;
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
    Long id;

    @NotNull
    String name;

    @NotNull
    String email;

    @NotNull
    String fcmToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    TargetGender gender;

    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    LocalDate birth;

    @NotNull
    String accountOwner;

    @NotNull
    @Enumerated(EnumType.STRING)
    AccountType accountType;

    @NotNull
    String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    InflowPath inflowPath;

    @Nullable
    String inflowPathEtc;

    @NotNull
    String phoneNumber;

    @NotNull
    PanelPlatform platform;

    @NotNull
    Boolean pushOn;

    @NotNull
    Boolean marketingAgree;


    // Default
    @NotNull
    String role;

    @Nullable
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @NotNull
    @Enumerated(EnumType.STRING)
    PanelStatus status;

    @NotNull
    Integer rewardCurrent;

    @NotNull
    Integer rewardTotal;

    @Nullable
    LocalDate signedUpAt;

    @Nullable
    LocalDateTime lastParticipatedAt;

    @Nullable
    @Column(length = 10)
    LocalDate deletedAt;


    // First Survey
    @Nullable
    Boolean english;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetCity city;

    @Nullable
    String district;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetFamily family;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetHouseType houseType;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetJob job;

    @Nullable
    String university;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetMajor major;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetMarriage marriage;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetMilitary military;

    @Nullable
    @Enumerated(EnumType.STRING)
    TargetPet pet;


    @Builder
    private Panel(String name, String email, String fcmToken, TargetGender gender,
                 LocalDate birth, String accountOwner, AccountType accountType, String accountNumber,
                 PanelStatus status, InflowPath inflowPath, String inflowPathEtc, String phoneNumber,
                 PanelPlatform platform, Boolean pushOn, Boolean marketingAgree,
                 Integer rewardCurrent, Integer rewardTotal, LocalDate signedUpAt, LocalDateTime lastParticipatedAt,
                  Boolean english, TargetCity city, String district,
                  TargetFamily family, TargetHouseType houseType, TargetJob job,
                  String university, TargetMajor major,
                  TargetMarriage marriage, TargetMilitary military, TargetPet pet,
                  String role, AuthProvider authProvider) {
        this.name = name;
        this.email = email;
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
        this.district = district;
        this.family = family;
        this.houseType = houseType;
        this.job = job;
        this.university = university;
        this.major = major;
        this.marriage = marriage;
        this.military = military;
        this.pet = pet;

        this.role = role;
        this.authProvider = authProvider;
    }


    public static Panel ofExisting(PanelInfoDAO panelInfoDAO, PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO) {
        return Panel.builder()
                .name(panelInfoDAO.getName())
                .email(panelInfoDAO.getEmail())
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
                .district(panelInfoFirstSurveyDAO.getDistrict())
                .family(panelInfoFirstSurveyDAO.getFamily())
                .houseType(panelInfoFirstSurveyDAO.getHouseType())
                .job(panelInfoFirstSurveyDAO.getJob())
                .university(panelInfoFirstSurveyDAO.getUniversity())
                .major(panelInfoFirstSurveyDAO.getMajor())
                .marriage(panelInfoFirstSurveyDAO.getMarriage())
                .military(panelInfoFirstSurveyDAO.getMilitary())
                .pet(panelInfoFirstSurveyDAO.getPet())
                .role("ROLE_USER")
                .build();
    }

    public static Panel ofOAuth(OAuth2UserInfo oAuth2UserInfo, AuthProvider authProvider) {
        return Panel.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .fcmToken("")
                .gender(oAuth2UserInfo.getGender())
                .birth(oAuth2UserInfo.getBirth())
                .accountOwner("")
                .accountType(AccountType.KB)
                .accountNumber("")
                .status(PanelStatus.FS_YET)
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
                .district(null)
                .family(null)
                .houseType(null)
                .job(null)
                .university(null)
                .major(null)
                .marriage(null)
                .military(null)
                .pet(null)
                .role("ROLE_ANONYMOUS")
                .authProvider(authProvider)
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


