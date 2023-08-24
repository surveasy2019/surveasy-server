package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class PanelNotFound extends BaseErrorException {

    public static final PanelNotFound EXCEPTION = new PanelNotFound();

    private PanelNotFound() {
        super(PanelErrorCode.PANEL_NOT_FOUND);
    }
}
