package com.kampusmerdeka.officeorder.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Component
public class FileDeleteUtil {
    private static Path fileStorageLocation;

    @SneakyThrows
    public FileDeleteUtil(@Value("${officeorder.STORE_FILE_LOCATION}") String fileStorageLocation) {
        FileDeleteUtil.fileStorageLocation = Paths.get(Objects.requireNonNull(fileStorageLocation)).toAbsolutePath().normalize();
        Files.createDirectories(FileDeleteUtil.fileStorageLocation);
    }

    @SneakyThrows
    public static void delete(String filePath) {
        Path path = fileStorageLocation.resolve(filePath);
        Files.delete(path);
    }
}