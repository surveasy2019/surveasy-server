package surveasy.domain.response.exception;

import surveasy.global.error.BaseErrorException;

public class ResponseDuplicateData extends BaseErrorException {

    public static final ResponseDuplicateData EXCEPTION = new ResponseDuplicateData();

    public ResponseDuplicateData() {
        super(ResponseErrorCode.RESPONSE_DUPLICATE_DATA);
    }
}
