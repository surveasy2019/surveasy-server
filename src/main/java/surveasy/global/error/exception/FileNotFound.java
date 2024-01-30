package surveasy.global.error.exception;

import surveasy.global.error.BaseErrorException;
import surveasy.global.error.GlobalErrorCode;

public class FileNotFound extends BaseErrorException {

    public static final FileNotFound EXCEPTION = new FileNotFound();

    public FileNotFound() {
        super(GlobalErrorCode.FILE_NOT_FOUND);
    }
}
