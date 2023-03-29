package com.kampusmerdeka.officeorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be empty")
    private String name;

    @NotNull(message = "description must not be null")
    @NotBlank(message = "description must not be empty")
    private String description;

    private MultipartFile image;

    @NotNull(message = "city id must not be null")
    @Min(value = 0, message = "invalid value of city id")
    private Long cityId;
}
