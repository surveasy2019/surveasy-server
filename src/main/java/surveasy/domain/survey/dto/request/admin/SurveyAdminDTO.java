package surveasy.domain.survey.dto.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.option.SurveyHeadcount;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SurveyAdminDTO {

    private SurveyStatus status;

    private String noticeToPanel;

    private Integer reward;

    private String link;

    private LocalDateTime dueDate;

    private SurveyHeadcount headCount;
}
