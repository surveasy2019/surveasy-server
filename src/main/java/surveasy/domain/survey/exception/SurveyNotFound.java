package surveasy.domain.survey.exception;

import surveasy.global.error.BaseErrorException;

public class SurveyNotFound extends BaseErrorException {

    public static final SurveyNotFound EXCEPTION = new SurveyNotFound();


    private SurveyNotFound() {
        super(SurveyErrorCode.SURVEY_NOT_FOUND);
    }
}
