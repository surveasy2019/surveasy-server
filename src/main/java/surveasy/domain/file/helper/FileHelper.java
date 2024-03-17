package surveasy.domain.file.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import surveasy.domain.file.domain.File;
import surveasy.domain.file.dto.response.FileAdminListResponse;
import surveasy.domain.file.exception.FileNotFound;
import surveasy.domain.file.mapper.FileMapper;
import surveasy.domain.file.repository.FileRepository;
import surveasy.domain.file.vo.S3SavedFileVo;

@Component
@RequiredArgsConstructor
public class FileHelper {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final S3Helper s3Helper;

    public Long saveFile(S3SavedFileVo s3SavedFileVo) {
        File file = fileMapper.toEntity(s3SavedFileVo);
        return fileRepository.save(file).getId();
    }

    public FileAdminListResponse getFileAdminList(Pageable pageable) {
        return FileAdminListResponse.from(fileRepository.findAllBy(pageable));
    }

    public ResponseEntity<UrlResource> downloadFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileNotFound.EXCEPTION);
        return s3Helper.downloadFile(file.getOriginalFilename());
    }

    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileNotFound.EXCEPTION);
        s3Helper.deleteFile(file.getOriginalFilename());
        fileRepository.delete(file);
    }
}
