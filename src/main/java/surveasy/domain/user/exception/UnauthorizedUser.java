package surveasy.domain.user.exception;

import surveasy.global.error.BaseErrorException;

public class UnauthorizedUser extends BaseErrorException {
    public static final UnauthorizedUser EXCEPTION = new UnauthorizedUser();
    private UnauthorizedUser() {
        super(UserErrorCode.UNAUTHORIZED_USER);
    }
}
