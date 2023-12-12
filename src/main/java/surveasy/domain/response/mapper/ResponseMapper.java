package surveasy.domain.response.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.vo.ResponseHistoryVo;
import surveasy.domain.survey.domain.Survey;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

    public Response toEntity(Panel panel, Survey survey, ResponseCreateRequestDTO responseCreateRequestDTO) {
        return Response.of(panel, survey, responseCreateRequestDTO);
    }

    public ResponseIdResponse toResponseIdResponse(Long responseId) {
        return ResponseIdResponse.from(responseId);
    }

    public ResponseHistoryListResponse toResponseHistoryList(String type, List<ResponseHistoryVo> responseList, PageInfo pageInfo) {
        return ResponseHistoryListResponse.from(type, pageInfo, responseList);
    }
}
