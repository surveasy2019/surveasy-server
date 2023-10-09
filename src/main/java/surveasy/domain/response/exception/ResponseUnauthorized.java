package surveasy.domain.response.exception;

import surveasy.global.error.BaseErrorException;

public class ResponseUnauthorized extends BaseErrorException {

    public static final ResponseUnauthorized EXCEPTION = new ResponseUnauthorized();

    private ResponseUnauthorized() {
        super(ResponseErrorCode.RESPONSE_UNAUTHORIZED);
    }
}
