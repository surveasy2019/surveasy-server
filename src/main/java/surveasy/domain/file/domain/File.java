package surveasy.domain.file.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String originalFilename;

    @NotNull
    private String s3Url;

    @NotNull
    private LocalDate createdAt;

    @Builder
    private File(String originalFilename, String s3Url) {
        this.originalFilename = originalFilename;
        this.s3Url = s3Url;
        this.createdAt = LocalDate.now();
    }

    public static File of(String originalFilename, String s3Url) {
        return File.builder()
                .originalFilename(originalFilename)
                .s3Url(s3Url)
                .build();
    }
}
