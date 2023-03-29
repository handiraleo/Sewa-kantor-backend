package com.kampusmerdeka.officeorder.dto.repsonse;

import com.kampusmerdeka.officeorder.entity.Price;
import com.kampusmerdeka.officeorder.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponse {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;
    private Double length;
    private Double width;
    private Double height;
    private Unit.Type type;
    private String typeLabel;
    private Long buildingId;
    private String building;
    private Long price;
    private Price.Type priceType;
    private List<UnitImageResponse> images;
}
