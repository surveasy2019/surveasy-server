package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.Panel;

@Getter
public class PanelHomeInfoResponse {

    String name;

    Long count;

    Integer rewardCurrent;

    Integer rewardTotal;

    @Builder
    private PanelHomeInfoResponse(String name, Long count, Integer rewardCurrent, Integer rewardTotal) {
        this.name = name;
        this.count = count;
        this.rewardCurrent = rewardCurrent;
        this.rewardTotal = rewardTotal;
    }

    public static PanelHomeInfoResponse from(Panel panel, Long count) {
        return PanelHomeInfoResponse.builder()
                .name(panel.getName())
                .count(count)
                .rewardCurrent(panel.getRewardCurrent())
                .rewardTotal(panel.getRewardTotal())
                .build();
    }
}
