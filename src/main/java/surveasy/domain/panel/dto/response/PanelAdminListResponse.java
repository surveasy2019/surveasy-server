package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import surveasy.domain.panel.domain.Panel;
import surveasy.global.common.dto.PageInfo;

import java.util.List;

@Getter
public class PanelAdminListResponse {
    private List<Panel> panelList;
    private PageInfo pageInfo;

    @Builder
    private PanelAdminListResponse(List<Panel> panelList, PageInfo pageInfo) {
        this.panelList = panelList;
        this.pageInfo = pageInfo;
    }

    public static PanelAdminListResponse from(Page<Panel> panelList) {
        PageInfo pageInfo = PageInfo.from(panelList);
        return PanelAdminListResponse.builder()
                .panelList(panelList.getContent())
                .pageInfo(pageInfo)
                .build();
    }
}
