package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyNotReleased extends BaseErrorException {

    public static final SurveyNotReleased EXCEPTION = new SurveyNotReleased();

    private SurveyNotReleased() {
        super(SurveyErrorCode.SURVEY_NOT_RELEASED);
    }
}
