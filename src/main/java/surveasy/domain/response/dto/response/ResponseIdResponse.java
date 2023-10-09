package surveasy.domain.response.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseIdResponse {

    private Long responseId;

    @Builder
    private ResponseIdResponse(Long responseId) {
        this.responseId = responseId;
    }

    public static ResponseIdResponse from(Long responseId) {
        return ResponseIdResponse.builder()
                .responseId(responseId)
                .build();
    }
}
