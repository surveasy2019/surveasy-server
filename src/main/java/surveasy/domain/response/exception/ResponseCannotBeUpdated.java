package surveasy.domain.response.exception;

import surveasy.global.error.BaseErrorException;

public class ResponseCannotBeUpdated extends BaseErrorException {
    public static final ResponseCannotBeUpdated EXCEPTION = new ResponseCannotBeUpdated();

    private ResponseCannotBeUpdated() {
        super(ResponseErrorCode.RESPONSE_CANNOT_BE_UPDATED);
    }
}
