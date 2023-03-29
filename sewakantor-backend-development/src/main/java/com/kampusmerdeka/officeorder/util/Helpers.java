package com.kampusmerdeka.officeorder.util;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Base64;

@Component
public class Helpers {
    private static String protocol;
    private static String host;
    private static Integer port;
    private static String contextPath;

    private Helpers(
            @Value("${officeorder.PROTOCOL}") String protocol, @Value("${officeorder.HOST}") String host,
            @Value("${officeorder.PORT}") Integer port, @Value("${server.servlet.context-path}") String contextPath) {
        Helpers.protocol = protocol;
        Helpers.host = host;
        Helpers.port = port;
        Helpers.contextPath = contextPath;
    }


    @SneakyThrows
    public static String resourceToBase64(Resource resource) {
        byte[] fileContent = FileUtils.readFileToByteArray(resource.getFile());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    @SneakyThrows
    public static String setFileUrl(String path) {
        if (path == null) return null;
        path = path.replace("\\", "/");
        String resourcePath = String.format("%s/v1/resources%s",
                contextPath.length() > 1 ? contextPath : "",
                path.startsWith("/") ? path : '/' + path);
        return new URL(protocol, host, port, resourcePath).toString();
    }
}