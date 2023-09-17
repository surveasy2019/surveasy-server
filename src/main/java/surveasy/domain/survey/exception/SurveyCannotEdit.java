package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyCannotEdit extends BaseErrorException {

    public static final SurveyCannotEdit EXCEPTION = new SurveyCannotEdit();

    private SurveyCannotEdit() {
        super(SurveyErrorCode.SURVEY_CANNOT_EDIT);
    }
}
