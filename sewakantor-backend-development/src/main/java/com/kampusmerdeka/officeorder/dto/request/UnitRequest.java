package com.kampusmerdeka.officeorder.dto.request;

import com.kampusmerdeka.officeorder.entity.Price;
import com.kampusmerdeka.officeorder.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be empty")
    private String name;

    @NotNull(message = "description must not be null")
    @NotBlank(message = "description must not be empty")
    private String description;

    @NotNull(message = "building id must not be null")
    @Min(value = 0, message = "invalid value of building id")
    private Long buildingId;

    @NotNull(message = "capacity must not be null")
    @Positive(message = "capacity not valid")
    private Integer capacity;

    @NotNull(message = "length must not be null")
    @Positive(message = "length not valid")
    private Double length;

    @NotNull(message = "width must not be null")
    @Positive(message = "width not valid")
    private Double width;

    @NotNull(message = "height must not be null")
    @Positive(message = "height not valid")
    private Double height;

    @ValueOfEnum(enumClass = Unit.Type.class, message = "unit type not valid")
    private String unitType;

    @Positive(message = "price not valid")
    @NotNull(message = "price must not be null")
    private Long price;

    @ValueOfEnum(enumClass = Price.Type.class, message = "price type not valid")
    private String priceType;

    private List<MultipartFile> images;
}
