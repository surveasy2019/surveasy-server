package surveasy.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import surveasy.domain.user.domain.InflowPath;

public record UserSignUpRequestDto(
        @NotEmpty String email,
        @NotEmpty String password,
        @NotEmpty String name,
        @NotEmpty String phoneNumber,
        @NotNull InflowPath inflowPath,
        @NotNull String inflowPathDetail) {
}
