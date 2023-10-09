package surveasy.domain.response.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.vo.ResponseMyPageListItemVo;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class ResponseMyPageListResponse {

    private String type;

    private PageInfo pageInfo;

    private List<ResponseMyPageListItemVo> responseList;

    @Builder
    private ResponseMyPageListResponse(String type, PageInfo pageInfo, List<ResponseMyPageListItemVo> responseList) {
        this.type = type;
        this.pageInfo = pageInfo;
        this.responseList = responseList;
    }

    public static ResponseMyPageListResponse from(String type, PageInfo pageInfo, List<ResponseMyPageListItemVo> responseList) {
        return ResponseMyPageListResponse.builder()
                .type(type)
                .pageInfo(pageInfo)
                .responseList(responseList)
                .build();
    }
}
