package surveasy.domain.survey.dto.request.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.survey.domain.option.SurveyHeadcount;

@Getter
@NoArgsConstructor
public class SurveyUpdateRequestDto {

    private String title;

    private String link;

    private SurveyHeadcount headCount;

    private Integer price;
}
