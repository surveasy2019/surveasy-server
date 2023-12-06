package surveasy.domain.review.exception;

import surveasy.global.error.BaseErrorException;

public class ReviewDuplicateData extends BaseErrorException {

    public static final ReviewDuplicateData EXCEPTION = new ReviewDuplicateData();

    public ReviewDuplicateData() {
        super(ReviewErrorCode.REVIEW_DUPLICATE_DATA);
    }
}
