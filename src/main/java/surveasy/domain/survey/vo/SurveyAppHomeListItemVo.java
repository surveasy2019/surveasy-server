package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

@Getter
public class SurveyAppHomeListItemVo {

    private Long id;

    private String title;

    private Integer reward;

    private String link;

    private String noticeToPanel;

    @Builder
    public SurveyAppHomeListItemVo(Long id, String title, Integer reward, String link, String noticeToPanel) {
        this.id = id;
        this.title = title;
        this.reward = reward;
        this.link = link;
        this.noticeToPanel = noticeToPanel;
    }
}
