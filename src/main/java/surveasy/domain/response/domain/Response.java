package surveasy.domain.response.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.survey.domain.Survey;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean isSent;

    @NotNull
    private Boolean isValid;

    @NotNull
    private String imgUrl;

    @NotNull
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panel_id", nullable = false)
    private Panel panel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Builder
    private Response(String imgUrl, Panel panel, Survey survey) {
        this.isSent = false;
        this.isValid = true;
        this.imgUrl = imgUrl;
        this.createdAt = new Date();
        this.panel = panel;
        this.survey = survey;
    }

    public static Response of(Panel panel, Survey survey, ResponseCreateRequestDTO responseCreateRequestDTO) {
        return Response.builder()
                .panel(panel)
                .survey(survey)
                .imgUrl(responseCreateRequestDTO.getImgUrl())
                .build();
    }
}
