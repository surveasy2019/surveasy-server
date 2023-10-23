package surveasy.domain.activepanel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activepanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    Date createdAt;

    @NotNull
    String totalList;

    @NotNull
    String maleList;

    @NotNull
    String femaleList;


    @Builder
    private Activepanel(String totalList, String maleList, String femaleList) {
        this.createdAt = new Date();
        this.totalList = totalList;
        this.maleList = maleList;
        this.femaleList = femaleList;
    }

    public static Activepanel of(String totalList, String maleList, String femaleList) {
        return Activepanel.builder()
                .totalList(totalList)
                .maleList(maleList)
                .femaleList(femaleList)
                .build();
    }
}
