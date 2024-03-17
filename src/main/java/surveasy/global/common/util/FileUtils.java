package surveasy.global.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import surveasy.global.error.exception.FileNotFound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private static final String FILE_LOCATION = "output" + File.separator;
    private static final String FOLDER = "output";

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(FILE_LOCATION + fileName);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));
            if(resource.exists()) {
                return resource;
            } else {
                throw FileNotFound.EXCEPTION;
            }
        } catch (IOException e) {
            throw FileNotFound.EXCEPTION;
        }
    }

    public void deleteAllFiles() {
        File folder = new File(FOLDER);
        try {
            if(folder.exists()) {
                org.apache.tomcat.util.http.fileupload.FileUtils.cleanDirectory(folder);   // 하위 폴더, 파일 삭제
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
