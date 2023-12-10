package surveasy.domain.response.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import surveasy.domain.response.dto.request.Response2CreateDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ResponseStatus status;

    @NotNull
    private String imgUrl;

    @NotNull
    @CreatedDate
    private Timestamp createdAt;

    @NotNull
    private Long panelId;

    @NotNull
    private Long surveyId;

    @Builder
    private Response2(String imgUrl, Long panelId, Long surveyId) {
        this.status = ResponseStatus.PARTICIPATED;
        this.imgUrl = imgUrl;
        this.createdAt = Timestamp.from(Instant.now());
        this.panelId = panelId;
        this.surveyId = surveyId;
    }

    public static Response2 of(Response2CreateDTO response2CreateDTO) {
        return Response2.builder()
                .imgUrl(response2CreateDTO.getImgUrl())
                .panelId(response2CreateDTO.getPanelId())
                .surveyId(response2CreateDTO.getSurveyId())
                .build();
    }
}
