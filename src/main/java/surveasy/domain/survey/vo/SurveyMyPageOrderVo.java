package surveasy.domain.survey.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveySpendTime;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.global.common.util.string.DateAndStringUtils;
import surveasy.global.common.util.string.ListAndStringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SurveyMyPageOrderVo {

    private Long id;

    private Long sid;

    private String title;

    private Boolean isDone;

    private SurveyHeadcount headCount;

    private Integer responseCount;

    private SurveySpendTime spendTime;

    private List<TargetAge> targetAgeList;
    private TargetGender targetGender;

    private SurveyStatus status;

    private Integer price;

    private SurveyIdentity identity;

    private String link;

    private String uploadedAt;

    private String dueDate;

    private Long reviewId;

    @Builder
    public SurveyMyPageOrderVo(Long id, Long sid, String title, SurveyHeadcount headCount, Integer responseCount,
                               SurveySpendTime spendTime, String targetAgeListStr, TargetGender targetGender, SurveyStatus status,
                               Integer price, SurveyIdentity identity, String link,
                               LocalDateTime uploadedAt, LocalDateTime dueDate, Long reviewId) {
        this.id = id;
        this.sid = sid;
        this.title = title;
        this.isDone = status.equals(SurveyStatus.DONE);
        this.headCount = headCount;
        this.responseCount = responseCount;
        this.spendTime = spendTime;
        this.targetAgeList = ListAndStringUtils.strToTargetAgeList(targetAgeListStr);
        this.targetGender = targetGender;
        this.status = status;
        this.price = price;
        this.identity = identity;
        this.link = link;
        this.uploadedAt = DateAndStringUtils.localDateTimeToString(uploadedAt);
        this.dueDate = DateAndStringUtils.localDateTimeToString(dueDate);
        this.reviewId = reviewId;
    }
}
