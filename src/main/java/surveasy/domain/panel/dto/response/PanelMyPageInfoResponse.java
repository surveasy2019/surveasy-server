package surveasy.domain.panel.dto.response;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.Panel;
import surveasy.global.common.util.DateAndStringUtil;

@Getter
public class PanelMyPageInfoResponse {

    String name;

    String birth;

    String gender;

    String email;

    String phoneNumber;

    String accountNumber;

    String accountType;

    Boolean english;

    @Builder
    private PanelMyPageInfoResponse(String name, String birth, String gender, String email,
                                    String phoneNumber, String accountType, String accountNumber, Boolean english) {
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.english = english;
    }

    public static PanelMyPageInfoResponse from(Panel panel) {
        String[] genderStr = new String[]{"남", "여"};

        return PanelMyPageInfoResponse.builder()
                .name(panel.getName())
                .birth(DateAndStringUtil.dateToStringYMD(panel.getBirth()))
                .gender(genderStr[panel.getGender()])
                .email(panel.getEmail())
                .phoneNumber(panel.getPhoneNumber())
                .accountType(panel.getAccountType())
                .accountNumber(panel.getAccountNumber())
                .english(panel.getEnglish())
                .build();
    }
}
