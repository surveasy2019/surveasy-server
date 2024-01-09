package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class NotRefreshToken extends BaseErrorException {

    public static final NotRefreshToken EXCEPTION = new NotRefreshToken();
    public NotRefreshToken() {
        super(PanelErrorCode.NOT_REFRESH_TOKEN);
    }
}
