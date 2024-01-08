package surveasy.domain.panel.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.domain.option.AccountType;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.global.common.util.DateAndStringUtil;

import java.time.LocalDate;

@Getter
public class PanelInfoVo {

    private final String name;

    private final String birth;

    private final String gender;

    private final String email;

    private final String phoneNumber;

    private final String accountOwner;

    private final String accountType;

    private final String accountNumber;

    private final Boolean english;

    @Builder
    public PanelInfoVo(String name, LocalDate birth, TargetGender gender, String email,
                       String phoneNumber, String accountOwner, AccountType accountType,
                       String accountNumber, Boolean english) {
        this.name = name;
        this.birth = DateAndStringUtil.localDateToString(birth);
        this.gender = gender.getValue();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountOwner = accountOwner;
        this.accountType = accountType.getValue();
        this.accountNumber = accountNumber;
        this.english = english;
    }

    public static PanelInfoVo from(Panel panel) {
        return PanelInfoVo.builder()
                .name(panel.getName())
                .birth(panel.getBirth())
                .gender(panel.getGender())
                .email(panel.getEmail())
                .phoneNumber(panel.getPhoneNumber())
                .accountOwner(panel.getAccountOwner())
                .accountType(panel.getAccountType())
                .accountNumber(panel.getAccountNumber())
                .english(panel.getEnglish())
                .build();
    }
}
