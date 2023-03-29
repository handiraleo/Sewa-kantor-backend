package com.kampusmerdeka.officeorder.dto.repsonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceResponse {
    private Long id;
    private String name;
    private String thumbnail;
    private String description;
    private String address;
    private List<BuildingTypeResponse> types;
    private Integer unit;
    private Double rating;
    private List<FacilityResponse> facilities;
    private List<NearbyPlaceResponse> nearbyPlaces;
    private Long price;
}