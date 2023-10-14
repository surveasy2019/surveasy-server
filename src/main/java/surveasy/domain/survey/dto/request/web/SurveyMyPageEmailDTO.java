package surveasy.domain.survey.dto.request.web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyMyPageEmailDTO {

    @NotNull
    private String email;
}
