package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyCannotDelete extends BaseErrorException {

    public static final SurveyCannotDelete EXCEPTION = new SurveyCannotDelete();

    private SurveyCannotDelete() {
        super(SurveyErrorCode.SURVEY_CANNOT_DELETE);
    }
}
