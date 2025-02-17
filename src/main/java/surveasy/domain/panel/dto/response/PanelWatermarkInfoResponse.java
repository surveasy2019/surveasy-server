package surveasy.domain.panel.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.panel.vo.PanelWatermarkVo;

@Builder(access = AccessLevel.PRIVATE)
public record PanelWatermarkInfoResponse(
        PanelWatermarkVo panelWatermarkInfo
) {
    public static PanelWatermarkInfoResponse of(PanelWatermarkVo panelWatermarkVo) {
        return PanelWatermarkInfoResponse.builder()
                .panelWatermarkInfo(panelWatermarkVo)
                .build();
    }
}
