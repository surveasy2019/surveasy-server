package surveasy.domain.response.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.domain.Response;

import java.util.List;

@Getter
public class AdminSurveyResponseListResponse {

    private List<Response> responseList;

    @Builder
    private AdminSurveyResponseListResponse(List<Response> responseList) {
        this.responseList = responseList;
    }

    public static AdminSurveyResponseListResponse from(List<Response> responseList) {
        return AdminSurveyResponseListResponse.builder()
                .responseList(responseList)
                .build();
    }
}
