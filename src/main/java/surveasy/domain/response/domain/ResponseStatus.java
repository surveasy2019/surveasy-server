package surveasy.domain.response.domain;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    PARTICIPATED("참여 완료"),
    WRONG("잘못된 응답"),
    WAITING("정산 대기"),
    DONE("정산 완료");

    private final String value;

    ResponseStatus(String value) {
        this.value = value;
    }
}
