package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class PanelNotFoundFB extends BaseErrorException {
    public static final PanelNotFoundFB EXCEPTION = new PanelNotFoundFB();

    private PanelNotFoundFB() {
        super(PanelErrorCode.PANEL_NOT_FOUND_FB);
    }
}
