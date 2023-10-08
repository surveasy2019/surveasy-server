package surveasy.domain.panel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.panel.dto.request.PanelInfoDAO;
import surveasy.domain.panel.dto.request.PanelInfoFirstSurveyDAO;
import surveasy.domain.panel.dto.request.PanelSignUpDTO;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Panel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String role;

    @NotNull
    String uid;

    @NotNull
    String name;

    @NotNull
    String email;

    @NotNull
    String fcmToken;

    @NotNull
    Integer gender;

    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    Date birth;

    @NotNull
    String accountOwner;

    @NotNull
    String accountType;

    @NotNull
    String accountNumber;

    @NotNull
    Boolean didFirstSurvey;

    @NotNull
    String inflowPath;

    @Nullable
    Date lastParticipatedAt;

    @NotNull
    Boolean marketingAgree;

    @NotNull
    String phoneNumber;

    @NotNull
    Integer platform;

    @NotNull
    Boolean pushOn;

    @Nullable
    Date signedUpAt;

    @NotNull
    Integer rewardCurrent;

    @NotNull
    Integer rewardTotal;

    @Nullable
    Boolean snsAuth;

    @Nullable
    String snsUid;


    // First Survey

    @Nullable
    Boolean english;

    @Nullable
    String city;

    @Nullable
    String district;

    @Nullable
    String family;

    @Nullable
    String houseType;

    @Nullable
    String job;

    @Nullable
    String major;

    @Nullable
    Integer married;

    @Nullable
    Integer military;

    @Nullable
    String pet;

    @Nullable
    String university;


    @Builder
    public Panel(String uid, String name, String email, String fcmToken,
                 Integer gender, Date birth,
                 String accountOwner, String accountType, String accountNumber,
                 Boolean didFirstSurvey,
                 String inflowPath, Date lastParticipatedAt,
                 Boolean marketingAgree, String phoneNumber,
                 Integer platform, Boolean pushOn, Date signedUpAt,
                 Integer rewardCurrent, Integer rewardTotal,
                 Boolean snsAuth, String snsUid,
                 Boolean english, String city, String district,
                 String family, String houseType, String job,
                 String major, Integer married, Integer military,
                 String pet, String university) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = gender;
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
        this.english = english;
        this.city = city;
        this.district = district;
        this.family = family;
        this.houseType = houseType;
        this.job = job;
        this.major = major;
        this.married = married;
        this.military = military;
        this.pet = pet;
        this.university = university;

        this.role = "ROLE_USER";
    }


    public static Panel ofExisting(PanelInfoDAO panelInfoDAO, PanelInfoFirstSurveyDAO panelInfoFirstSurveyDAO) {
        return Panel.builder()
                .uid(panelInfoDAO.getUid())
                .name(panelInfoDAO.getName())
                .email(panelInfoDAO.getEmail())
                .fcmToken(panelInfoDAO.getFcmToken())
                .gender(panelInfoDAO.getGender())
                .birth(panelInfoDAO.getBirth())
                .accountOwner(panelInfoDAO.getAccountOwner())
                .accountType(panelInfoDAO.getAccountType())
                .accountNumber(panelInfoDAO.getAccountNumber())
                .didFirstSurvey(panelInfoDAO.getDidFirstSurvey())
                .inflowPath(panelInfoDAO.getInflowPath())
                .lastParticipatedAt(panelInfoDAO.getLastParticipatedAt())
                .marketingAgree(panelInfoDAO.getMarketingAgree())
                .phoneNumber(panelInfoDAO.getPhoneNumber())
                .platform(panelInfoDAO.getPlatform())
                .pushOn(panelInfoDAO.getPushOn())
                .signedUpAt(panelInfoDAO.getSignedUpAt())
                .rewardCurrent(panelInfoDAO.getRewardCurrent())
                .rewardTotal(panelInfoDAO.getRewardTotal())
                .snsAuth(panelInfoDAO.getSnsAuth())
                .snsUid(panelInfoDAO.getSnsUid())

                .english(panelInfoFirstSurveyDAO.getEnglish())
                .city(panelInfoFirstSurveyDAO.getCity())
                .district(panelInfoFirstSurveyDAO.getDistrict())
                .family(panelInfoFirstSurveyDAO.getFamily())
                .houseType(panelInfoFirstSurveyDAO.getHouseType())
                .job(panelInfoFirstSurveyDAO.getJob())
                .major(panelInfoFirstSurveyDAO.getMajor())
                .married(panelInfoFirstSurveyDAO.getMarried())
                .military(panelInfoFirstSurveyDAO.getMilitary())
                .pet(panelInfoFirstSurveyDAO.getPet())
                .university(panelInfoFirstSurveyDAO.getUniversity())
                .build();
    }

    public static Panel ofNew(PanelSignUpDTO panelSignUpDTO) {
        return Panel.builder()
                .uid(panelSignUpDTO.getEmail())
                .name(panelSignUpDTO.getName())
                .email(panelSignUpDTO.getEmail())
                .fcmToken(panelSignUpDTO.getFcmToken())
                .gender(panelSignUpDTO.getGender())
                .birth(panelSignUpDTO.getBirth())
                .accountOwner(panelSignUpDTO.getAccountOwner())
                .accountType(panelSignUpDTO.getAccountType())
                .accountNumber(panelSignUpDTO.getAccountNumber())
                .didFirstSurvey(false)
                .inflowPath(panelSignUpDTO.getInflowPath())
                .lastParticipatedAt(null)
                .marketingAgree(panelSignUpDTO.getMarketingAgree())
                .phoneNumber(panelSignUpDTO.getPhoneNumber())
                .platform(panelSignUpDTO.getPlatform())
                .pushOn(panelSignUpDTO.getPushOn())
                .signedUpAt(panelSignUpDTO.getSignedUpAt())
                .rewardCurrent(0)
                .rewardTotal(0)

                .english(null)
                .city(null)
                .district(null)
                .family(null)
                .houseType(null)
                .job(null)
                .major(null)
                .married(null)
                .military(null)
                .pet(null)
                .university(null)
                .build();
    }
}


