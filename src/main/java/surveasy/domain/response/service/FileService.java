package surveasy.domain.response.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import surveasy.global.error.exception.FileNotFound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private static final String FILE_LOCATION = "output\\";

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
}
