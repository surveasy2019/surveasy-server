package surveasy.domain.survey.dto.request.admin;

import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.option.SurveyHeadcount;

import java.time.LocalDateTime;

public record AdminSurveyUpdateDto(
        SurveyStatus status,
        String noticeToPanel,
        Integer reward,
        String link,
        LocalDateTime dueDate,
        SurveyHeadcount headCount
) {
}
