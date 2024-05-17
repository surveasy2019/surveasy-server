package surveasy.domain.panel.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.panel.vo.PanelAdminCsvVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PanelAdminCsvListResponse(
        List<PanelAdminCsvVo> panelList
) {
    public static PanelAdminCsvListResponse of(List<PanelAdminCsvVo> panelList) {
        return PanelAdminCsvListResponse.builder()
                .panelList(panelList)
                .build();
    }
}
