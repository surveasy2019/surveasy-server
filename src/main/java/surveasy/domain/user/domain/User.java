package surveasy.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import surveasy.domain.user.dto.request.UserSignUpRequestDto;
import surveasy.domain.user.vo.FirebaseUserInfoVo;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "user")
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Size(max = 128)
    @JsonIgnore
    private String password;

    @NotNull
    private String phoneNumber;

    @Enumerated
    private InflowPath inflowPath;

    private String inflowPathDetail;

    @NotNull
    @Builder.Default
    private Integer point = 0;

    @NotNull
    @Builder.Default
    @Enumerated
    private UserRole role = UserRole.USER;

    public static User createUserFromFirebase(FirebaseUserInfoVo firebaseUserInfoVo) {
        return User.builder()
                .name(firebaseUserInfoVo.name())
                .email(firebaseUserInfoVo.email())
                .password(firebaseUserInfoVo.password())
                .phoneNumber(firebaseUserInfoVo.phoneNumber())
                .inflowPath(firebaseUserInfoVo.inflowPath())
                .inflowPathDetail(firebaseUserInfoVo.inflowPathDetail())
                .point(firebaseUserInfoVo.point())
                .build();
    }

    public static User createUser(UserSignUpRequestDto signUpRequestDto) {
        return User.builder()
                .email(signUpRequestDto.email())
                .password(signUpRequestDto.password())
                .name(signUpRequestDto.name())
                .phoneNumber(signUpRequestDto.phoneNumber())
                .inflowPath(signUpRequestDto.inflowPath())
                .inflowPathDetail(signUpRequestDto.inflowPathDetail())
                .build();
    }
}
