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

    private Integer spendTime;

    private Integer progress;

    private Integer price;

    private String link;

    private String uploadedAt;

    private String dueDate;

    @Builder
    public SurveyMyPageOrderListItemVo(Long id, Long sid, String title, Boolean isDone,
                                       Integer headCount, Long nowCount, Integer spendTime,
                                       Integer progress, Integer price, String link,
                                       String uploadedAt, String dueDate) {
        this.id = id;
        this.sid = sid;
        this.title = title;
        this.isDone = isDone;
        this.headCount = headCount;
        this.nowCount = nowCount;
        this.spendTime = spendTime;
        this.progress = progress;
        this.price = price;
        this.link = link;
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
                .spendTime(survey.getSpendTime())
                .progress(survey.getProgress())
                .price(survey.getPrice())
                .link(survey.getLink())
                .uploadedAt(uploadedAt)
                .dueDate(dueDate)
                .build();
    }
}
