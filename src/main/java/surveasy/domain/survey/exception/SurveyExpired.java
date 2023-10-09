package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyExpired extends BaseErrorException {

    public static final SurveyExpired EXCEPTION = new SurveyExpired();

    private SurveyExpired() {
        super(SurveyErrorCode.SURVEY_EXPIRED);
    }
}
