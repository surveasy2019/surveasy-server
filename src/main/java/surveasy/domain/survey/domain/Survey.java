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
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.global.common.function.ListAndString;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long sid;

    @NotNull
    private Integer progress;

    @NotNull
    private Boolean english;

    @NotNull
    private String accountName;

    @NotNull
    private Date dueDate;

    @NotNull
    private String institute;

    @NotNull
    private String link;

    @Nullable
    private String notice;

    @Nullable
    private String noticeToPanel;

    @NotNull
    private Integer reward;

    @NotNull
    private Integer pointAdd;

    @NotNull
    private Integer price;

    @NotNull
    private Integer priceDiscounted;

    @NotNull
    private Integer priceIdentity;

    @NotNull
    private Integer headCount;

    @NotNull
    private Integer responseCount;

    @NotNull
    private Integer spendTime;

    @Nullable
    private String tarInput;

    @NotNull
    private String tarAge;

    @NotNull
    private Integer tarGender;

    @NotNull
    private String title;

    @NotNull
    private Date uploadedAt;

    @NotNull
    private String email;

    @NotNull
    private String username;




    @Nullable
    private SurveyHeadcount eHeadcount = SurveyHeadcount.HEAD_30;

    @Nullable
    private SurveySpendTime eSpendTime = SurveySpendTime.TIME_0;

    @Nullable
    private SurveyLanguage eLanguage = SurveyLanguage.KOR;

    @Nullable
    private SurveyIdentity eIdentity = SurveyIdentity.GRADUATE;




    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    @JsonIgnore
    private List<Response> responseList;

    @Builder
    public Survey(Boolean english,
                  String accountName,
                  Date dueDate,
                  String institute,
                  String link,
                  String notice,
                  Integer pointAdd,
                  Integer price,
                  Integer priceDiscounted,
                  Integer priceIdentity,
                  Integer headCount,
                  Integer spendTime,
                  String tarInput,
                  List<Integer> tarAge,
                  Integer tarGender,
                  String title,
                  String email,
                  String username) {
        this.english = english;
        this.accountName = accountName;
        this.dueDate = dueDate;
        this.institute = institute;
        this.link = link;
        this.notice = notice;
        this.pointAdd = pointAdd;
        this.price = price;
        this.priceDiscounted = priceDiscounted;
        this.priceIdentity = priceIdentity;
        this.headCount = headCount;
        this.spendTime = spendTime;
        this.tarInput = tarInput;
        this.tarAge = ListAndString.listToString(tarAge);
        this.tarGender = tarGender;
        this.title = title;
        this.email = email;
        this.username = username;

        this.uploadedAt = new Date();
        this.sid = 0L;
        this.progress = 0;
        this.reward = 0;
        this.responseCount = 0;
    }

    public static Survey of(SurveyServiceDTO surveyServiceDTO) {
        return Survey.builder()
                .english(surveyServiceDTO.getEnglish())
                .accountName(surveyServiceDTO.getAccountName())
                .dueDate(surveyServiceDTO.getDueDate())
                .institute(surveyServiceDTO.getInstitute())
                .link(surveyServiceDTO.getLink())
                .notice(surveyServiceDTO.getNotice())
                .pointAdd(surveyServiceDTO.getPointAdd())
                .price(surveyServiceDTO.getPrice())
                .priceDiscounted(surveyServiceDTO.getPriceDiscounted())
                .priceIdentity(surveyServiceDTO.getPriceIdentity())
                .headCount(surveyServiceDTO.getHeadCount())
                .spendTime(surveyServiceDTO.getSpendTime())
                .tarInput(surveyServiceDTO.getTarInput())
                .tarAge(surveyServiceDTO.getTarAge())
                .tarGender(surveyServiceDTO.getTarGender())
                .title(surveyServiceDTO.getTitle())
                .email(surveyServiceDTO.getEmail())
                .username(surveyServiceDTO.getUsername())
                .build();
    }
}
