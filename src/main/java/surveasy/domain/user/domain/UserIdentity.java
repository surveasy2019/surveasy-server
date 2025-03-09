package surveasy.domain.user.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserIdentity {
    MID_HIGH(1, "중/고등학생"),
    UNDERGRADUATE(2, "대학생"),
    GRADUATE(3, "대학원생"),
    NONE(4, "할인 대상이 아님");

    private Integer code;
    private String desc;

    public static UserIdentity ofFirebase(String firebaseIdentity) {
        return switch (firebaseIdentity) {
            case "중/고등학생" -> MID_HIGH;
            case "대학생" -> UNDERGRADUATE;
            case "대학원생" -> GRADUATE;
            default -> NONE;
        };
    }
}
