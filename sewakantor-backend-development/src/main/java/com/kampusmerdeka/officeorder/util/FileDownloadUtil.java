package com.kampusmerdeka.officeorder.util;

import com.kampusmerdeka.officeorder.exception.FileStorageException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Component
public class FileDownloadUtil {
    private static Path fileStorageLocation;

    public FileDownloadUtil(@Value("${officeorder.STORE_FILE_LOCATION}") String fileStorageLocation) {
        FileDownloadUtil.fileStorageLocation = Paths.get(Objects.requireNonNull(fileStorageLocation)).toAbsolutePath().normalize();
        try {
            Files.createDirectories(FileDownloadUtil.fileStorageLocation);
        } catch (Exception ex) {
            log.error("errot create directory; {}", ex.getLocalizedMessage());
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @SneakyThrows
    public static Resource getFileAsResource(String filePath) {
        Path path = fileStorageLocation.resolve(filePath);

        Resource resource = new UrlResource(path.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            log.error("file not found" + filePath);
            throw new IOException("File not found " + filePath);
        }
    }
}