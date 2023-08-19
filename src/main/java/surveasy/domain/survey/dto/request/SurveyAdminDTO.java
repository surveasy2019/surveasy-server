package surveasy.domain.survey.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyAdminDTO {

    private Integer progress;

    private String noticeToPanel;

    private Integer panelReward;

    private String link;
}
