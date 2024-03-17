package surveasy.domain.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.file.dto.response.FileAdminListResponse;
import surveasy.domain.file.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "File")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "파일 목록")
    @GetMapping("")
    public FileAdminListResponse getFileAdminList(@PageableDefault(size = 10) Pageable pageable) {
        return fileService.getFileAdminList(pageable);
    }

    @Operation(summary = "파일 다운로드")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable Long fileId) {
        return fileService.downloadFile(fileId);
    }

    @Operation(summary = "파일 삭제")
    @DeleteMapping("/{fileId}")
    public void deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
    }
}
