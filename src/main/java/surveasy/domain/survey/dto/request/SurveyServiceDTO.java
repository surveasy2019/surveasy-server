package surveasy.domain.survey.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyServiceDTO {

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
    private List<Integer> tarAge;

    @NotNull
    private Integer tarGender;

    @NotNull
    private String title;

}
