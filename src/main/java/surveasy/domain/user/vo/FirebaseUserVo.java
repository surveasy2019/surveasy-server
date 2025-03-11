package surveasy.domain.user.vo;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FirebaseUserVo(
        String name,
        String email,
        String phoneNumber
) {
    public static FirebaseUserVo of(String name, String email, String phoneNumber) {
        return FirebaseUserVo.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
