package surveasy.domain.survey.dto.request.web;

import surveasy.domain.survey.domain.option.SurveyHeadcount;

public record SurveyMyPageEditDto(
        String title,
        String link,
        SurveyHeadcount headCount,
        Integer price
) {
}
