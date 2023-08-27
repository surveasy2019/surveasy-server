package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorCode;
import surveasy.global.error.BaseErrorException;

public class PanelNotFound extends BaseErrorException {

    public static final PanelNotFound EXCEPTION_DB = new PanelNotFound(PanelErrorCode.PANEL_NOT_FOUND_DB);
    public static final PanelNotFound EXCEPTION_FB = new PanelNotFound(PanelErrorCode.PANEL_NOT_FOUND_FB);

    private PanelNotFound(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
