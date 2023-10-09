package surveasy.domain.response.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.survey.domain.Survey;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

    public Response toEntity(Panel panel, Survey survey, ResponseCreateRequestDTO responseCreateRequestDTO) {
        return Response.of(panel, survey, responseCreateRequestDTO);
    };

    public ResponseIdResponse toResponseIdResponse(Long responseId) {
        return ResponseIdResponse.from(responseId);
    }
}
