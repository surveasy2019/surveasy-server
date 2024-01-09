package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class RefreshTokenNotFound extends BaseErrorException {

    public static final RefreshTokenNotFound EXCEPTION = new RefreshTokenNotFound();
    public RefreshTokenNotFound() {
        super(PanelErrorCode.REFRESH_NOT_FOUND);
    }
}
