package surveasy.domain.response.exception;

import surveasy.global.error.BaseErrorException;

public class ResponseNotFound extends BaseErrorException {
    public static final ResponseNotFound EXCEPTION = new ResponseNotFound();

    private ResponseNotFound() {
        super(ResponseErrorCode.RESPONSE_NOT_FOUND);
    }
}
