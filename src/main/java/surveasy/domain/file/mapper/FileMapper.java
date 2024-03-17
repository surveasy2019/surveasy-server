package surveasy.domain.file.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.file.domain.File;
import surveasy.domain.file.vo.S3SavedFileVo;

@Component
public class FileMapper {

    public File toEntity(S3SavedFileVo s3SavedFileVo) {
        return File.of(s3SavedFileVo.getOriginalFileName(), s3SavedFileVo.getS3Url());
    }
}
