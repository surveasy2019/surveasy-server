package surveasy.domain.survey.dto.request.web;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyMyPageEditDTO {

    private String title;

    private String link;

    private Integer headCount;

    private Integer price;
}
