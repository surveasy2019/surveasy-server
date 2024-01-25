package surveasy.domain.panel.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AccountType;

@Getter
public class PanelResponseInfoVo {

    private final Integer rewardCurrent;

    private final String accountOwner;

    private final String accountType;

    private final String accountNumber;

    @Builder
    private PanelResponseInfoVo(Integer rewardCurrent, String accountOwner, AccountType accountType, String accountNumber) {
        this.rewardCurrent = rewardCurrent;
        this.accountOwner = accountOwner;
        this.accountType = accountType.getValue();
        this.accountNumber = accountNumber;
    }

    public static PanelResponseInfoVo from(Panel panel) {
        return PanelResponseInfoVo.builder()
                .rewardCurrent(panel.getRewardCurrent())
                .accountOwner(panel.getAccountOwner())
                .accountType(panel.getAccountType())
                .accountNumber(panel.getAccountNumber())
                .build();
    }
}
