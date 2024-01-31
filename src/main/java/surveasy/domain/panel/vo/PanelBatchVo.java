package surveasy.domain.panel.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AccountType;

@Getter
public class PanelBatchVo {

    private final String accountOwner;
    private final String accountType;
    private final String accountNumber;
    private final Integer rewardTemp;

    private final String sender = "서베이지";

    @Builder
    private PanelBatchVo(String accountOwner, AccountType accountType, String accountNumber, Integer rewardTemp) {
        this.accountOwner = accountOwner;
        this.accountType = accountType.getValue();
        this.accountNumber = accountNumber;
        this.rewardTemp = rewardTemp;
    }

    public static PanelBatchVo from(Panel panel) {
        return PanelBatchVo.builder()
                .accountOwner(panel.getAccountOwner())
                .accountType(panel.getAccountType())
                .accountNumber(panel.getAccountNumber())
                .rewardTemp(panel.getRewardTemp())
                .build();
    }
}
