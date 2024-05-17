package surveasy.domain.panel.vo;

import lombok.Builder;
import lombok.Getter;
import surveasy.domain.panel.domain.option.InflowPath;
import surveasy.domain.panel.domain.option.PanelStatus;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;

@Getter
public class PanelAdminCsvVo {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String inflowPath;
    private String inflowPathEtc;
    private LocalDate signedUpAt;
    private PanelStatus status;

    @Builder
    public PanelAdminCsvVo(Long id,
                           String name,
                           LocalDate birth,
                           TargetGender gender,
                           InflowPath inflowPath,
                           String inflowPathEtc,
                           LocalDate signedUpAt,
                           PanelStatus status) {
        this.id = id;
        this.name = name;
        this.age = LocalDate.now().getYear() - birth.getYear() + 1;
        this.gender = gender.getValue();
        this.inflowPath = inflowPath.getValue();
        this.inflowPathEtc = inflowPathEtc;
        this.signedUpAt = signedUpAt;
        this.status = status;
    }
}
