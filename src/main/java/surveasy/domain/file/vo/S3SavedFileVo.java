package surveasy.domain.file.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class S3SavedFileVo {

    @NotNull
    private String originalFileName;

    @NotNull
    private String s3Url;

    @Builder
    private S3SavedFileVo(String originalFileName, String s3Url) {
        this.originalFileName = originalFileName;
        this.s3Url = s3Url;
    }

    public static S3SavedFileVo of(String originalFileName, String s3Url) {
        return S3SavedFileVo.builder()
                .originalFileName(originalFileName)
                .s3Url(s3Url)
                .build();
    }
}
