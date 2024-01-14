package surveasy.domain.panel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import surveasy.domain.panel.domain.option.AuthProvider;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;

@Getter
public class OAuth2UserInfo {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private TargetGender gender;        // MALE or FEMALE

    @NotNull
    private LocalDate birth;            // yyyy-MM-dd

    @NotNull
    private AuthProvider authProvider;  // KAKAO or APPLE
}
