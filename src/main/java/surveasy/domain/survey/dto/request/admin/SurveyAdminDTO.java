package surveasy.domain.survey.dto.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyAdminDTO {

    private Integer progress;

    private String noticeToPanel;

    private Integer reward;

    private String link;
}
