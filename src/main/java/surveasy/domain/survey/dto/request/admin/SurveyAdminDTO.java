package surveasy.domain.survey.dto.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.survey.domain.SurveyStatus;

@Getter
@NoArgsConstructor
public class SurveyAdminDTO {

    private SurveyStatus status;

    private String noticeToPanel;

    private Integer reward;

    private String link;
}
