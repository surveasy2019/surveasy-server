package surveasy.domain.response.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.vo.ResponseHistoryVo;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class ResponseHistoryListResponse {

    private String type;

    private PageInfo pageInfo;

    private List<ResponseHistoryVo> responseList;

    @Builder
    private ResponseHistoryListResponse(String type, PageInfo pageInfo, List<ResponseHistoryVo> responseList) {
        this.type = type;
        this.pageInfo = pageInfo;
        this.responseList = responseList;
    }

    public static ResponseHistoryListResponse from(String type, PageInfo pageInfo, List<ResponseHistoryVo> responseList) {
        return ResponseHistoryListResponse.builder()
                .type(type)
                .pageInfo(pageInfo)
                .responseList(responseList)
                .build();
    }
}
