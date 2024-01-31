package surveasy.domain.response.domain;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    CREATED("참여 완료"),
    WRONG("잘못됨"),
    UPDATED("수정됨"),
    WAITING("정산 대기"),
    DONE("정산 완료");

    private final String value;

    ResponseStatus(String value) {
        this.value = value;
    }
}
