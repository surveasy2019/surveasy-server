package surveasy.domain.panel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.panel.domain.option.AccountType;

@Getter
@NoArgsConstructor
public class PanelInfoUpdateDTO {

    String phoneNumber;

    String accountOwner;

    AccountType accountType;

    String accountNumber;

    Boolean english;
}
