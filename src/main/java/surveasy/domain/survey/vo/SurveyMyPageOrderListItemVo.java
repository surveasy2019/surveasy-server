package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

@Getter
public class SurveyMyPageOrderListItemVo {

    private Long id;

    private Long sid;

    private String title;

    private Boolean isDone;

    private Integer headCount;

    private Long nowCount;

    private Integer progress;

    private String uploadedAt;

    private String dueDate;

    @Builder
    public SurveyMyPageOrderListItemVo(Long id, Long sid, String title,
                                       Boolean isDone, Integer headCount, Long nowCount,
                                       Integer progress, String uploadedAt, String dueDate) {
        this.id = id;
        this.sid = sid;
        this.title = title;
        this.isDone = isDone;
        this.headCount = headCount;
        this.nowCount = nowCount;
        this.progress = progress;
        this.uploadedAt = uploadedAt;
        this.dueDate = dueDate;
    }

    public static SurveyMyPageOrderListItemVo from(Survey survey) {
        Boolean isDone = (survey.getProgress() > 2) ? true : false;
        String uploadedAt = survey.getUploadedAt().toString().substring(0, 10);
        String dueDate = survey.getDueDate().toString().substring(0, 10);

        return SurveyMyPageOrderListItemVo.builder()
                .id(survey.getId())
                .sid(survey.getSid())
                .title(survey.getTitle())
                .isDone(isDone)
                .headCount(survey.getHeadCount())
                .nowCount(100L)
                .progress(survey.getProgress())
                .uploadedAt(uploadedAt)
                .dueDate(dueDate)
                .build();
    }
}
