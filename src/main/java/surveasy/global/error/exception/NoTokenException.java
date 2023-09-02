package surveasy.global.error.exception;

import surveasy.global.error.BaseErrorException;
import surveasy.global.error.GlobalErrorCode;

public class NoTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NoTokenException();


    private NoTokenException() {
        super(GlobalErrorCode.NO_TOKEN);
    }
}
