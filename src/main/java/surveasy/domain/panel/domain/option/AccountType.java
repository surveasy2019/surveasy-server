package surveasy.domain.panel.domain.option;

import lombok.Getter;

@Getter
public enum AccountType {

    KB("국민"),
    HANA("하나"),
    WOORI("우리"),
    SHINHAN("신한"),
    NH("농협"),
    SH("수협"),
    IBK("IBK 기업"),
    MG("새마을금고"),
    KAKAO("카카오뱅크"),
    TOSS("토스뱅크");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }
}
