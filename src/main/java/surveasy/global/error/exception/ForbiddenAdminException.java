package surveasy.global.error.exception;

import surveasy.global.error.BaseErrorException;
import surveasy.global.error.GlobalErrorCode;

public class ForbiddenAdminException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ForbiddenAdminException();

    private ForbiddenAdminException() {
        super(GlobalErrorCode.FORBIDDEN_ADMIN);
    }
}
