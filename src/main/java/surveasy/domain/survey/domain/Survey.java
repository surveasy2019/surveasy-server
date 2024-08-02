package surveasy.domain.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.payment.domain.Payment;
import surveasy.domain.response.domain.Response;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.option.SurveySpendTime;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.user.domain.User;
import surveasy.global.common.util.string.ListAndStringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static surveasy.global.common.util.EntityUpdateValueUtils.updateValue;

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

    @NotNull
    private String description;

    @Nullable
    private String notice;


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


    @JsonIgnore
    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<Response> responseList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    private Survey(User user, SurveyHeadcount headCount, SurveySpendTime spendTime, LocalDateTime dueDate,
                  TargetGender targetGender, List<TargetAge> targetAgeList,
                  SurveyLanguage language, SurveyIdentity identity,
                  String title, String targetInput, String institute,
                  String link, String description, String notice) {
        this.user = user;

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
        this.description = description;
        this.notice = notice;

        this.sid = 0L;
        this.uploadedAt = LocalDateTime.now();
        this.reward = 0;
        this.responseCount = 0;
    }

    public static Survey from(User user, SurveyCreateRequestDto surveyCreateRequestDto) {
        return Survey.builder()
                .user(user)
                .headCount(surveyCreateRequestDto.getHeadCount())
                .spendTime(surveyCreateRequestDto.getSpendTime())
                .dueDate(surveyCreateRequestDto.getDueDate())
                .targetGender(surveyCreateRequestDto.getTargetGender())
                .targetAgeList(surveyCreateRequestDto.getTargetAgeList())
                .language(surveyCreateRequestDto.getLanguage())
                .identity(surveyCreateRequestDto.getIdentity())
                .title(surveyCreateRequestDto.getTitle())
                .targetInput(surveyCreateRequestDto.getTargetInput())
                .institute(surveyCreateRequestDto.getInstitute())
                .link(surveyCreateRequestDto.getLink())
                .description(surveyCreateRequestDto.getDescription())
                .notice(surveyCreateRequestDto.getNotice())
                .build();
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void updateSurvey(SurveyUpdateRequestDto surveyUpdateRequestDto) {
        this.title = updateValue(this.title, surveyUpdateRequestDto.getTitle());
        this.link = updateValue(this.link, surveyUpdateRequestDto.getLink());
        this.headCount = updateValue(this.headCount, surveyUpdateRequestDto.getHeadCount());
    }
}
