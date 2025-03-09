package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyCannotRefund extends BaseErrorException {

    public static final SurveyCannotRefund EXCEPTION = new SurveyCannotRefund();

    private SurveyCannotRefund() {
        super(SurveyErrorCode.SURVEY_CANNOT_REFUND);
    }
}
