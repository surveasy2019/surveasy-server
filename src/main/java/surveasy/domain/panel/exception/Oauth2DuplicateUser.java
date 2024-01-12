package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class Oauth2DuplicateUser extends BaseErrorException {

    public static final Oauth2DuplicateUser EXCEPTION = new Oauth2DuplicateUser();

    private Oauth2DuplicateUser() {
        super(PanelErrorCode.OAUTH2_DUPLICATE_USER);
    }
}
