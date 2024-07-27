package surveasy.domain.user.dto.request;

public record UserSignInRequestDto(
        String email,
        String password
) {
}
