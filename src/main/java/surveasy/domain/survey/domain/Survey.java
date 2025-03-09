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
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.user.domain.User;
import surveasy.global.common.util.string.ListAndStringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static surveasy.global.common.util.EntityUpdateValueUtils.updateValue;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "survey")
@Entity
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
    @Builder.Default
    private Long sid = 0L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SurveyStatus status = SurveyStatus.CREATED;

    @NotNull
    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Nullable
    private String noticeToPanel;

    @NotNull
    @Builder.Default
    private Integer reward = 0;

    @NotNull
    @Builder.Default
    private Integer responseCount = 0;

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

    public static Survey createSurvey(User user, SurveyCreateRequestDto surveyCreateRequestDto) {
        return Survey.builder()
                .user(user)
                .headCount(surveyCreateRequestDto.headCount())
                .spendTime(surveyCreateRequestDto.spendTime())
                .dueDate(surveyCreateRequestDto.dueDate())
                .targetGender(surveyCreateRequestDto.targetGender())
                .targetAgeListStr(ListAndStringUtils.listToStr(surveyCreateRequestDto.targetAgeList()))
                .language(surveyCreateRequestDto.language())
                .identity(surveyCreateRequestDto.identity())
                .title(surveyCreateRequestDto.title())
                .targetInput(surveyCreateRequestDto.targetInput())
                .institute(surveyCreateRequestDto.institute())
                .link(surveyCreateRequestDto.link())
                .description(surveyCreateRequestDto.description())
                .notice(surveyCreateRequestDto.notice())
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

    public void updateAdminSurvey(AdminSurveyUpdateRequestDto adminSurveyUpdateRequestDto) {
        this.status = updateValue(this.status, adminSurveyUpdateRequestDto.status());
        this.noticeToPanel = updateValue(this.noticeToPanel, adminSurveyUpdateRequestDto.noticeToPanel());
        this.reward = updateValue(this.reward, adminSurveyUpdateRequestDto.reward());
        this.link = updateValue(this.link, adminSurveyUpdateRequestDto.link());
        this.dueDate = updateValue(this.dueDate, adminSurveyUpdateRequestDto.dueDate());
        this.headCount = updateValue(this.headCount, adminSurveyUpdateRequestDto.headCount());
    }

    public void updateSurveySid(Long sid) {
        this.sid = updateValue(this.sid, sid);
    }

    public void updateSurveyResponseCount(Integer responseCount) {
        this.responseCount = updateValue(this.responseCount, responseCount);
    }

    public void updateSurveyStatus(SurveyStatus status) {
        this.status = updateValue(this.status, status);
    }

    public void updateReviewId(Long reviewId) {
        this.reviewId = updateValue(this.reviewId, reviewId);
    }
}
