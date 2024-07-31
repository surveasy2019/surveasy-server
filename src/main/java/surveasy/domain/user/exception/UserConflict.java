package surveasy.domain.user.exception;

import surveasy.global.error.BaseErrorException;

public class UserConflict extends BaseErrorException {
    public static final UserConflict EXCEPTION = new UserConflict();
    private UserConflict() {
        super(UserErrorCode.CONFLICT_USER);
    }
}
