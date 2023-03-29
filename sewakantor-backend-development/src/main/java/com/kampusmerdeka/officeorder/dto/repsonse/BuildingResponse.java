package com.kampusmerdeka.officeorder.dto.repsonse;

import com.kampusmerdeka.officeorder.entity.BuildingFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Long complexId;
    private String complex;
    private List<BuildingImageResponse> images;
    private List<FacilityResponse> facilities;
}
