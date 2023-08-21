package surveasy.domain.panel.dto.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class PanelInfoDTO {

    String name;

    String email;

    String fcmToken;

    Integer gender;

    Date birth;

    String accountOwner;

    String accountType;

    Boolean didFirstSurvey;

    Integer inflowPath;

    Date lastParticipatedAt;

    Boolean marketingAgree;

    String phoneNumber;

    Integer platform;

    Integer pushOn;

    Date signedUpAt;

    Integer rewardCurrent;

    Integer rewardTotal;

    Boolean snsAuth;

    String snsUid;


    // First Survey

    Boolean english;

    String city;

    String district;

    String family;

    Integer houseType;

    String job;

    String major;

    Integer married;

    Integer military;

    String pet;

    String university;
}
