package surveasy.global.error.exception;

import surveasy.global.error.BaseErrorException;
import surveasy.global.error.GlobalErrorCode;

public class TokenValidateException extends BaseErrorException{

    public static final BaseErrorException EXCEPTION = new TokenValidateException();

    private TokenValidateException() {
        super(GlobalErrorCode.INVALID_AUTH_TOKEN);
    }
}
