package surveasy.domain.response.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import surveasy.domain.response.domain.ResponseStatus;

@Getter
@NoArgsConstructor
public class ResponseUpdateRequestDTO {

    private String imgUrl;

    private ResponseStatus status;
}
