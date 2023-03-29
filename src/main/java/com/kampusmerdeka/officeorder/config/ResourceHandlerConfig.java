package com.kampusmerdeka.officeorder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {
    @Value("${officeorder.STORE_FILE_LOCATION}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/v1/resources/**")
                .addResourceLocations(String.format("file:%s", (fileDir.endsWith("/") ? fileDir : fileDir + "/")));
    }
}