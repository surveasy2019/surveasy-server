package surveasy.domain.response.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseMyPageListResponse;
import surveasy.domain.response.vo.ResponseMyPageListItemVo;
import surveasy.domain.survey.domain.Survey;
import surveasy.global.common.dto.PageInfo;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

    public Response toEntity(Panel panel, Survey survey, ResponseCreateRequestDTO responseCreateRequestDTO) {
//        return Response.of(panel, survey, responseCreateRequestDTO);
        return null;
    }

    public ResponseIdResponse toResponseIdResponse(Long responseId) {
        return ResponseIdResponse.from(responseId);
    }

    public ResponseMyPageListResponse toResponseMyPageListResponse(String type, List<Response> responseList, PageInfo pageInfo) {
        List<ResponseMyPageListItemVo> responseMyPageListItemVos = responseList.stream()
                .sorted(Comparator.comparing(Response::getId).reversed())
                .map(ResponseMyPageListItemVo::from)
                .toList();

        return ResponseMyPageListResponse.from(type, pageInfo, responseMyPageListItemVos);
    }
}
