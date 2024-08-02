package surveasy.global.error.toss;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import surveasy.global.error.BaseErrorException;

import java.io.IOException;
import java.io.InputStream;

import static surveasy.global.error.GlobalErrorCode.TOSS_ERROR_FORMAT;

public class TossErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        TossErrorResponse errorResponse = parseTossErrorResponse(response);
        return getExceptionWithMessageForErrorCode(errorResponse);
    }

    private TossErrorResponse parseTossErrorResponse(Response response) {
        TossErrorResponse message;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, TossErrorResponse.class);
        } catch (IOException e) {
            throw new BaseErrorException(TOSS_ERROR_FORMAT);
        }
        return message;
    }

    private Exception getExceptionWithMessageForErrorCode(TossErrorResponse errorResponse) {
        TossErrorCode tossErrorCode = TossErrorCode.of(errorResponse.code(), errorResponse.message());
        return new BaseErrorException(tossErrorCode);
    }
}
