package surveasy.global.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import surveasy.domain.file.vo.S3SavedFileVo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class S3Utils {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3SavedFileVo saveAggregationCsvFile() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource(new File("output" + File.separator + LocalDate.now() + ".csv"));
        File csvFile = fileSystemResource.getFile();
        String originalFilename = csvFile.getName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(csvFile.length());
        metadata.setContentType("text/csv");

        amazonS3.putObject(bucket, originalFilename, fileSystemResource.getInputStream(), metadata);
        return S3SavedFileVo.of(originalFilename, amazonS3.getUrl(bucket, originalFilename).toString());
    }

    public ResponseEntity<UrlResource> downloadFile(String originalFilename) {
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));
        String contentDisposition = "attachment; filename=\"" + originalFilename + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    public void deleteFile(String originalFilename) {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}
