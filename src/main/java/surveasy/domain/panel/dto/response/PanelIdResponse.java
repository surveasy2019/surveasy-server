package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PanelIdResponse {

    private Long panelId;

    @Builder
    private PanelIdResponse (Long panelId) {
        this.panelId = panelId;
    }

    public static PanelIdResponse from(Long panelId) {
        return PanelIdResponse.builder()
                .panelId(panelId)
                .build();
    }
}
