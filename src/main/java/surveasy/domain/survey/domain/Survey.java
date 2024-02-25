package surveasy.domain.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.response.domain.Response;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.option.SurveySpendTime;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.global.common.util.ListAndStringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* Service 1 */
    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveyHeadcount headCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveySpendTime spendTime;

    @NotNull
    private LocalDateTime dueDate;

    @NotNull
    private TargetGender targetGender;

    @NotNull
    private String targetAgeListStr;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveyLanguage language;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveyIdentity identity;


    /* Service 2 */
    @NotNull
    private String title;

    @Nullable
    private String targetInput;

    @NotNull
    private String institute;

    @NotNull
    private String link;

    @Nullable
    private String notice;

    @NotNull
    private String accountName;

    @NotNull
    private Integer price;

    @NotNull
    private Integer priceDiscounted;

    @NotNull
    private Integer pointAdd;


    /* User */
    @NotNull
    private String email;

    @NotNull
    private String username;


    /* Default */
    @NotNull
    private Long sid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SurveyStatus status = SurveyStatus.CREATED;

    @NotNull
    private LocalDateTime uploadedAt;

    @Nullable
    private String noticeToPanel;

    @NotNull
    private Integer reward;

    @NotNull
    private Integer responseCount;

    @Nullable
    private Long reviewId;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonIgnore
    private List<Response> responseList;

    @Builder
    private Survey(SurveyHeadcount headCount, SurveySpendTime spendTime, LocalDateTime dueDate,
                  TargetGender targetGender, List<TargetAge> targetAgeList,
                  SurveyLanguage language, SurveyIdentity identity,
                  String title, String targetInput, String institute,
                  String link, String notice, String accountName,
                  Integer price, Integer priceDiscounted, Integer pointAdd,
                  String email, String username) {
        this.headCount = headCount;
        this.spendTime = spendTime;
        this.dueDate = dueDate;
        this.targetGender = targetGender;
        this.targetAgeListStr = ListAndStringUtils.listToStr(targetAgeList);
        this.language = language;
        this.identity = identity;

        this.title = title;
        this.targetInput = targetInput;
        this.institute = institute;
        this.link = link;
        this.notice = notice;
        this.accountName = accountName;
        this.price = price;
        this.priceDiscounted = priceDiscounted;
        this.pointAdd = pointAdd;

        this.email = email;
        this.username = username;

        this.sid = 0L;
        this.uploadedAt = LocalDateTime.now();
        this.reward = 0;
        this.responseCount = 0;
    }

    public static Survey from(SurveyServiceDTO surveyServiceDTO) {
        return Survey.builder()
                .headCount(surveyServiceDTO.getHeadCount())
                .spendTime(surveyServiceDTO.getSpendTime())
                .dueDate(surveyServiceDTO.getDueDate())
                .targetGender(surveyServiceDTO.getTargetGender())
                .targetAgeList(surveyServiceDTO.getTargetAgeList())
                .language(surveyServiceDTO.getLanguage())
                .identity(surveyServiceDTO.getIdentity())
                .title(surveyServiceDTO.getTitle())
                .targetInput(surveyServiceDTO.getTargetInput())
                .institute(surveyServiceDTO.getInstitute())
                .link(surveyServiceDTO.getLink())
                .notice(surveyServiceDTO.getNotice())
                .accountName(surveyServiceDTO.getAccountName())
                .price(surveyServiceDTO.getPrice())
                .priceDiscounted(surveyServiceDTO.getPriceDiscounted())
                .pointAdd(surveyServiceDTO.getPointAdd())
                .email(surveyServiceDTO.getEmail())
                .username(surveyServiceDTO.getUsername())
                .build();
    }
}
