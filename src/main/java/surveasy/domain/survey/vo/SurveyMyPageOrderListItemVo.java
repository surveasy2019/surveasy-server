package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.Survey;

import java.util.Date;

@Getter
public class SurveyMyPageOrderListItemVo {

    private Long id;

    private Long sid;

    private String title;

    private Boolean isDone;

    private Integer headCount;

    private Integer responseCount;

    private Integer spendTime;

    private Integer progress;

    private Integer price;

    private Integer priceIdentity;

    private String link;

    private String uploadedAt;

    private String dueDate;

    @Builder
    public SurveyMyPageOrderListItemVo(Long id, Long sid, String title,
                                       Integer headCount, Integer responseCount, Integer spendTime,
                                       Integer progress, Integer price, Integer priceIdentity, String link,
                                       Date uploadedAt, Date dueDate) {
        this.id = id;
        this.sid = sid;
        this.title = title;
        this.isDone = (progress > 2) ? true : false;
        this.headCount = headCount;
        this.responseCount = responseCount;
        this.spendTime = spendTime;
        this.progress = progress;
        this.price = price;
        this.priceIdentity = priceIdentity;
        this.link = link;
        this.uploadedAt = uploadedAt.toString().substring(0, 10);
        this.dueDate = dueDate.toString().substring(0, 10);;
    }
}
