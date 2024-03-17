package surveasy.domain.file.exception;

import surveasy.global.error.BaseErrorException;

public class FileNotFound extends BaseErrorException {

    public static final FileNotFound EXCEPTION = new FileNotFound();

    private FileNotFound() {
        super(FileErrorCode.FILE_NOT_FOUND);
    }
}
