package surveasy.domain.review.exception;

import surveasy.global.error.BaseErrorException;

public class ReviewNotAllowed extends BaseErrorException {

    public static final ReviewNotAllowed EXCEPTION = new ReviewNotAllowed();

    public ReviewNotAllowed() {
        super(ReviewErrorCode.REVIEW_NOT_ALLOWED);
    }
}
