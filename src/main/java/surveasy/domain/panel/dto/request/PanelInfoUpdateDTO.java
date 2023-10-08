package surveasy.domain.panel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PanelInfoUpdateDTO {

    String phoneNumber;

    String accountType;

    String accountNumber;

    Boolean english;
}
