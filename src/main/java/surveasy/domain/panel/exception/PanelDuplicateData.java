package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class PanelDuplicateData extends BaseErrorException {

    public static final PanelDuplicateData EXCEPTION = new PanelDuplicateData();

    public PanelDuplicateData() {
        super(PanelErrorCode.PANEL_DUPLICATE_DATA);
    }
}
