package surveasy.domain.panel.exception;

import surveasy.global.error.BaseErrorException;

public class MismatchPassword extends BaseErrorException {

    public static final MismatchPassword EXCEPTION = new MismatchPassword();
    private MismatchPassword() {
        super(PanelErrorCode.MISMATCH_PASSWORD);
    }
}
