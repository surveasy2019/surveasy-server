package surveasy.domain.survey.vo;

import lombok.Builder;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.option.SurveySpendTime;

public class SurveyVo {

    private int headcount;
    private String spendTime;
    private int language;
    private int identity;


    @Builder
    public SurveyVo(SurveyHeadcount headcount, SurveySpendTime spendTime, SurveyLanguage language, SurveyIdentity identity) {
        this.headcount = headcount.getMax();
        this.spendTime = spendTime.getStr();
        this.language = language.getIdx();
        this.identity = identity.getIdx();
    }
}
