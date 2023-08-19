package surveasy.domain.survey.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;

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
                  String title) {
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
        this.tarAge = listToString(tarAge);
        this.tarGender = tarGender;
        this.title = title;

        this.username = "김민동";
        this.email = "min@dong.com";
        this.uploadedAt = new Date();
        this.sid = 0L;
        this.progress = 0;
        this.reward = 0;
    }

    public String listToString(List<Integer> intList) {
        return intList.toString();
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
                .build();
    }
}
