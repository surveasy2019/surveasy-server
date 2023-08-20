package surveasy.domain.panel.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.panel.dto.request.PanelInfoDTO;

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
    String name;

    @NotNull
    String email;

    @NotNull
    String fcmToken;

    @NotNull
    Integer gender;

    @NotNull
    Date birth;

    @NotNull
    String accountOwner;

    @NotNull
    String accountType;

    @NotNull
    Boolean didFirstSurvey;

    @NotNull
    Integer inflowPath;

    @Nullable
    Date lastParticipatedAt;

    @NotNull
    Boolean marketingAgree;

    @NotNull
    String phoneNumber;

    @NotNull
    Integer platform;

    @NotNull
    Integer pushOn;

    @NotNull
    Date signedUpAt;

    @NotNull
    Integer rewardCurrent;

    @NotNull
    Integer rewardTotal;

    Boolean snsAuth;

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
    Integer houseType;

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
    public Panel(String name, String email, String fcmToken,
                 Integer gender, Date birth, String accountOwner,
                 String accountType, Boolean didFirstSurvey,
                 Integer inflowPath, Date lastParticipatedAt,
                 Boolean marketingAgree, String phoneNumber,
                 Integer platform, Integer pushOn, Date signedUpAt,
                 Integer rewardCurrent, Integer rewardTotal,
                 Boolean snsAuth, String snsUid,
                 Boolean english, String city, String district,
                 String family, Integer houseType, String job,
                 String major, Integer married, Integer military,
                 String pet, String university) {
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = gender;
        this.birth = birth;
        this.accountOwner = accountOwner;
        this.accountType = accountType;
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
    }


    public static Panel of(PanelInfoDTO panelInfoDTO) {
        return Panel.builder()
                .name(panelInfoDTO.getName())
                .email(panelInfoDTO.getEmail())
                .fcmToken(panelInfoDTO.getFcmToken())
                .gender(panelInfoDTO.getGender())
                .birth(panelInfoDTO.getBirth())
                .accountOwner(panelInfoDTO.getAccountOwner())
                .accountType(panelInfoDTO.getAccountType())
                .didFirstSurvey(panelInfoDTO.getDidFirstSurvey())
                .inflowPath(panelInfoDTO.getInflowPath())
                .lastParticipatedAt(panelInfoDTO.getLastParticipatedAt())
                .marketingAgree(panelInfoDTO.getMarketingAgree())
                .phoneNumber(panelInfoDTO.getPhoneNumber())
                .platform(panelInfoDTO.getPlatform())
                .pushOn(panelInfoDTO.getPushOn())
                .signedUpAt(panelInfoDTO.getSignedUpAt())
                .rewardCurrent(panelInfoDTO.getRewardCurrent())
                .rewardTotal(panelInfoDTO.getRewardTotal())
                .snsAuth(panelInfoDTO.getSnsAuth())
                .snsUid(panelInfoDTO.getSnsUid())
                .english(panelInfoDTO.getEnglish())
                .city(panelInfoDTO.getCity())
                .district(panelInfoDTO.getDistrict())
                .family(panelInfoDTO.getFamily())
                .houseType(panelInfoDTO.getHouseType())
                .job(panelInfoDTO.getJob())
                .major(panelInfoDTO.getMajor())
                .married(panelInfoDTO.getMarried())
                .military(panelInfoDTO.getMilitary())
                .pet(panelInfoDTO.getPet())
                .university(panelInfoDTO.getUniversity())
                .build();
    }
}


