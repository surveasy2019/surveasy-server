package surveasy.domain.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.survey.domain.Survey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer grade;

    @NotNull
    private String content;

    @NotNull
    private String createdAt;

    @NotNull
    private ReviewStatus status = ReviewStatus.INVISIBLE;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @OneToOne
    @JoinColumn(name = "id")
    private Survey survey;

    @Builder
    private Review(Survey survey, Integer grade, String content, String username, String email) {
        this.grade = grade;
        this.content = content;
        this.createdAt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.username = username;
        this.email = email;
        this.survey = survey;
    }

    public static Review of(Survey survey, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return Review.builder()
                .grade(reviewCreateRequestDTO.getGrade())
                .content(reviewCreateRequestDTO.getContent())
                .username(reviewCreateRequestDTO.getUsername())
                .email(reviewCreateRequestDTO.getEmail())
                .survey(survey)
                .build();
    }
}
