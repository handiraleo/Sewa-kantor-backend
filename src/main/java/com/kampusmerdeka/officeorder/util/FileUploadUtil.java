package com.kampusmerdeka.officeorder.util;

import com.kampusmerdeka.officeorder.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Component
public class FileUploadUtil {
    private static Path fileStorageLocation;

    public FileUploadUtil(@Value("${officeorder.STORE_FILE_LOCATION}") String fileStorageLocation) {
        FileUploadUtil.fileStorageLocation = Paths.get(Objects.requireNonNull(fileStorageLocation)).toAbsolutePath().normalize();
        try {
            Files.createDirectories(FileUploadUtil.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public static String saveFile(String directory, String fileName, MultipartFile multipartFile) throws IOException {

        if (!Files.exists(fileStorageLocation.resolve(directory))) {
            Files.createDirectories(fileStorageLocation.resolve(directory));
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        fileName = StringUtils.substring(fileName.replace(" ", "-"), fileName.length() - 32);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = Paths.get(directory).resolve(fileCode + "-" + fileName);
            Path fullFilePath = fileStorageLocation.resolve(filePath);
            Files.copy(inputStream, fullFilePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ioe) {
            log.error("Could not save file: {}", ioe.getLocalizedMessage());
            throw new IOException("Could not save file: {}", ioe);
        }
    }
}