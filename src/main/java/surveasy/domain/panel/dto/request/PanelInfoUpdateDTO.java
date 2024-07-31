package surveasy.domain.panel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.panel.domain.option.AccountType;

@Getter
@NoArgsConstructor
public class PanelInfoUpdateDTO {

    private String phoneNumber;

    private String accountOwner;

    private AccountType accountType;

    private String accountNumber;

    private Boolean english;

    private String fcmToken;
}
