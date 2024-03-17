package surveasy.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.file.dto.response.FileAdminListResponse;
import surveasy.domain.file.helper.FileHelper;
import surveasy.domain.file.vo.S3SavedFileVo;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileHelper fileHelper;

    @Transactional
    public Long saveFile(S3SavedFileVo s3SavedFileVo) {
        return fileHelper.saveFile(s3SavedFileVo);
    }

    @Transactional(readOnly = true)
    public FileAdminListResponse getFileAdminList(Pageable pageable) {
        return fileHelper.getFileAdminList(pageable);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<UrlResource> downloadFile(Long fileId) {
        return fileHelper.downloadFile(fileId);
    }

    public void deleteFile(Long fileId) {
        fileHelper.deleteFile(fileId);
    }
}
