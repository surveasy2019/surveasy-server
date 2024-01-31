package surveasy.domain.response.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.response.domain.Response;

@Getter
public class ResponseBatchVo {

    private Long id;
    private Long panelId;
    private Integer reward;

    @Builder
    private ResponseBatchVo(Long id, Long panelId, Integer reward) {
        this.id = id;
        this.panelId = panelId;
        this.reward = reward;
    }

    public static ResponseBatchVo from(Response response) {
        return ResponseBatchVo.builder()
                .id(response.getId())
                .panelId(response.getPanel().getId())
                .reward(response.getReward())
                .build();
    }
}
