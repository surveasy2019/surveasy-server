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
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDto;
import surveasy.global.common.util.ListAndStringUtils;

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


    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    @JsonIgnore
    private List<Response> responseList;

    @Builder
    private Survey(SurveyHeadcount headCount, SurveySpendTime spendTime, LocalDateTime dueDate,
                  TargetGender targetGender, List<TargetAge> targetAgeList,
                  SurveyLanguage language, SurveyIdentity identity,
                  String title, String targetInput, String institute,
                  String link, String description, String notice, String accountName,
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
        this.description = description;
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

    public static Survey createSurvey(SurveyCreateRequestDto surveyCreateRequestDto) {
        return Survey.builder()
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
                .accountName(surveyCreateRequestDto.getAccountName())
                .price(surveyCreateRequestDto.getPrice())
                .priceDiscounted(surveyCreateRequestDto.getPriceDiscounted())
                .pointAdd(surveyCreateRequestDto.getPointAdd())
                .email(surveyCreateRequestDto.getEmail())
                .username(surveyCreateRequestDto.getUsername())
                .build();
    }

    public void updateSurvey(AdminSurveyUpdateDto adminSurveyUpdateDto) {
        this.status = updateValue(this.status, adminSurveyUpdateDto.status());
        this.noticeToPanel = updateValue(this.noticeToPanel, adminSurveyUpdateDto.noticeToPanel());
        this.reward = updateValue(this.reward, adminSurveyUpdateDto.reward());
        this.link = updateValue(this.link, adminSurveyUpdateDto.link());
        this.dueDate = updateValue(this.dueDate, adminSurveyUpdateDto.dueDate());
        this.headCount = updateValue(this.headCount, adminSurveyUpdateDto.headCount());
    }

    public void updateSurveySid(Long sid) {
        this.sid = sid;
    }

    public void updateMySurvey(SurveyMyPageEditDto surveyMyPageEditDto) {
        this.title = updateValue(this.title, surveyMyPageEditDto.title());
        this.link = updateValue(this.link, surveyMyPageEditDto.link());
        this.headCount = updateValue(this.headCount, surveyMyPageEditDto.headCount());
        this.price = updateValue(this.price, surveyMyPageEditDto.price());
    }
}
