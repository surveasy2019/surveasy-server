package surveasy.domain.panel.vo;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.panel.domain.Panel;

@Builder(access = AccessLevel.PRIVATE)
public record PanelWatermarkVo(
        String name,
        String phoneNumber
) {
    public static PanelWatermarkVo of(Panel panel) {
        return PanelWatermarkVo.builder()
                .name(panel.getName())
                .phoneNumber(panel.getPhoneNumber().substring(panel.getPhoneNumber().length()-4))
                .build();
    }
}
