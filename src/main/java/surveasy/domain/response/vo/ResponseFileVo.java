package surveasy.domain.response.vo;

import lombok.Builder;

public class ResponseFileVo {

    private final String accountOwner;
    private final String accountNumber;
    private final Integer reward;

    @Builder
    private ResponseFileVo(String accountOwner, String accountNumber, Integer reward) {
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.reward = reward;
    }

    public static ResponseFileVo of(String accountOwner, String accountNumber, Integer reward) {
        return ResponseFileVo.builder()
                .accountOwner(accountOwner)
                .accountNumber(accountNumber)
                .reward(reward)
                .build();
    }
}
