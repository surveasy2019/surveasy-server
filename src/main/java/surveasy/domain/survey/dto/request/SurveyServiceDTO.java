package surveasy.domain.survey.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyServiceDTO {

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

    @NotNull
    private Integer point_add;

    @NotNull
    private Integer price;

    @NotNull
    private String priceIdentity;

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

}
