package surveasy.domain.survey.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long lastCheckedId;

    @NotNull
    private Integer progress;

    @NotNull
    private Boolean enTarget;

    @NotNull
    private String account_userName;

    @NotNull
    private String dueDate;

    @NotNull
    private String institute;

    @NotNull
    private String link;

    @Nullable
    private String notice;

    @Nullable
    private String noticeToPanel;

    @NotNull
    private Integer panelReward;

    @NotNull
    private Integer point_add;

    @NotNull
    private Integer price;

    @NotNull
    private String priceIdentity;

    // String -> Int 자료형 변경 필요
    @NotNull
    private Integer requiredHeadCount;

    @NotNull
    private String spendTime;

    @Nullable
    private String target;

    @NotNull
    private Integer targetingAge;

    @NotNull
    private Integer targetingAgeOption;

    @NotNull
    private String targetingAgeOptionList;

    @NotNull
    private Integer targetingGender;

    @NotNull
    private String targetingGenderOptionList;

    @NotNull
    private String title;

    @NotNull
    private Date uploadedAt;

    @NotNull
    private String uploader;

    @Builder
    public Survey(Boolean enTarget,
                  String account_userName,
                  String dueDate,
                  String institute,
                  String link,
                  String notice,
                  Integer point_add,
                  Integer price,
                  String priceIdentity,
                  Integer requiredHeadCount,
                  String spendTime,
                  String target,
                  Integer targetingAge,
                  Integer targetingAgeOption,
                  String targetingAgeOptionList,
                  Integer targetingGender,
                  String targetingGenderOptionList,
                  String title) {
        this.enTarget = enTarget;
        this.account_userName = account_userName;
        this.dueDate = dueDate;
        this.institute = institute;
        this.link = link;
        this.notice = notice;
        this.point_add = point_add;
        this.price = price;
        this.priceIdentity = priceIdentity;
        this.requiredHeadCount = requiredHeadCount;
        this.spendTime = spendTime;
        this.target = target;
        this.targetingAge = targetingAge;
        this.targetingAgeOption = targetingAgeOption;
        this.targetingAgeOptionList = targetingAgeOptionList;
        this.targetingGender = targetingGender;
        this.targetingGenderOptionList = targetingGenderOptionList;
        this.title = title;

        this.uploader = "mindong";
        this.uploadedAt = new Date();
        this.lastCheckedId = 0L;
        this.progress = 0;
        this.panelReward = 0;
    }

    public static Survey of(SurveyServiceDTO surveyServiceDTO) {
        return Survey.builder()
                .enTarget(surveyServiceDTO.getEnTarget())
                .account_userName(surveyServiceDTO.getAccount_userName())
                .dueDate(surveyServiceDTO.getDueDate())
                .institute(surveyServiceDTO.getInstitute())
                .link(surveyServiceDTO.getLink())
                .notice(surveyServiceDTO.getNotice())
                .point_add(surveyServiceDTO.getPoint_add())
                .price(surveyServiceDTO.getPrice())
                .priceIdentity(surveyServiceDTO.getPriceIdentity())
                .requiredHeadCount(surveyServiceDTO.getRequiredHeadCount())
                .spendTime(surveyServiceDTO.getSpendTime())
                .target(surveyServiceDTO.getTarget())
                .targetingAge(surveyServiceDTO.getTargetingAge())
                .targetingAgeOption(surveyServiceDTO.getTargetingAgeOption())
                .targetingAgeOptionList(surveyServiceDTO.getTargetingAgeOptionList())
                .targetingGender(surveyServiceDTO.getTargetingGender())
                .targetingGenderOptionList(surveyServiceDTO.getTargetingGenderOptionList())
                .title(surveyServiceDTO.getTitle())
                .build();
    }
}
