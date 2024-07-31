package surveasy.domain.survey.dto.request.web;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.option.SurveySpendTime;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyCreateRequestDto {

    /* Service 1 */
    @NotNull
    private SurveyHeadcount headCount;

    @NotNull
    private SurveySpendTime spendTime;

    @NotNull
    private LocalDateTime dueDate;

    @NotNull
    private TargetGender targetGender;

    @NotNull
    private List<TargetAge> targetAgeList;

    @NotNull
    private SurveyLanguage language;

    @NotNull
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
}
