package surveasy.domain.panel.vo;

import lombok.Builder;

public class PanelFileVo {

    private final String accountOwner;
    private final String accountNumber;
    private final Integer rewardSum;

    @Builder
    private PanelFileVo(String accountOwner, String accountNumber, Integer rewardSum) {
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.rewardSum = rewardSum;
    }

    public static PanelFileVo of(String accountOwner, String accountNumber, Integer rewardSum) {
        return PanelFileVo.builder()
                .accountOwner(accountOwner)
                .accountNumber(accountNumber)
                .rewardSum(rewardSum)
                .build();
    }
}
