package surveasy.domain.user.vo;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.user.domain.InflowPath;
import surveasy.domain.user.domain.UserIdentity;

@Builder(access = AccessLevel.PRIVATE)
public record FirebaseUserInfoVo(
        String name,
        String email,
        String password,
        String phoneNumber,
        UserIdentity identity,
        InflowPath inflowPath,
        String inflowPathDetail,
        Integer point
) {
    public static FirebaseUserInfoVo of(String name,
                                        String email,
                                        String password,
                                        String phoneNumber,
                                        UserIdentity identity,
                                        InflowPath inflowPath,
                                        String inflowPathDetail,
                                        Integer point) {
        return FirebaseUserInfoVo.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .identity(identity)
                .inflowPath(inflowPath)
                .inflowPathDetail(inflowPathDetail)
                .point(point)
                .build();
    }
}
