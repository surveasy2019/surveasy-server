package surveasy.domain.panel.domain.option;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum PanelRole {

    ROLE_ADMIN("운영진"),
    ROLE_USER("사용자"),
    ROLE_ANONYMOUS("가입자");

    @JsonValue
    private final String userRole;

    @JsonCreator
    public static PanelRole parsing(String input) {
        return Stream.of(PanelRole.values())
                .filter(category -> category.getUserRole().equals(input))
                .findFirst()
                .orElse(null);
    }
}