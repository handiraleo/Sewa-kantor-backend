package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.*;
import com.kampusmerdeka.officeorder.entity.*;
import com.kampusmerdeka.officeorder.repository.BuildingRepository;
import com.kampusmerdeka.officeorder.util.Helpers;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpaceService {
    @Autowired
    private BuildingRepository buildingRepository;

    public ResponseEntity<Object> getAll() {

        List<SpaceResponse> result = new ArrayList<>();
        Iterable<Building> buildingIterable = buildingRepository.findAll();

        buildingIterable.forEach(building -> {
            if (!building.getUnits().isEmpty()) result.add(getResponse(building));
        });

        return ResponseUtil.ok("all space", result);
    }


    public ResponseEntity<Object> getOne(Long id) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isEmpty()) return ResponseUtil.notFound("space not found");

        Building building = buildingOptional.get();
        return ResponseUtil.ok("space detail", getResponse(building));
    }

    private SpaceResponse getResponse(Building building) {
        String thumbnail = !building.getBuildingImages().isEmpty()
                ? building.getBuildingImages().stream().findFirst().isPresent()
                ? building.getBuildingImages().stream().findFirst().get().getPath()
                : null
                : null;

        // get facilities
        List<FacilityResponse> facilityResponses = new ArrayList<>();
        building.getBuildingFacilities().forEach(buildingFacility -> {
            Facility facility = buildingFacility.getFacility();
            facilityResponses.add(FacilityResponse.builder()
                    .id(facility.getId())
                    .name(facility.getName())
                    .icon(Helpers.setFileUrl(facility.getIcon()))
                    .build());
        });

        Set<Unit> buildingUnits = building.getUnits();

        // get unit type count
        List<BuildingTypeResponse> types = new ArrayList<>();
        Map<String, Long> typeCount = buildingUnits.stream().collect(Collectors.groupingBy(unit -> unit.getType().label, Collectors.counting()));
        typeCount.forEach((k, v) -> types.add(BuildingTypeResponse.builder().name(k).count(v).build()));

        // get nearby rating & price
        var review = new Object() {
            Integer count = 0;
            Integer value = 0;

            public Double getRating() {
                return (count == 0 && value == 0) ? 0.0 : BigDecimal.valueOf(this.value.doubleValue() / this.count).setScale(1, RoundingMode.HALF_UP).doubleValue();
            }
        };
        Set<Long> price = new HashSet<>();
        buildingUnits.forEach(unit -> {
            review.count += unit.getReviews().size();
            unit.getReviews().forEach(review1 -> review.value += review1.getStar());
            price.addAll(unit.getPrices().stream()
                    .filter(t -> Price.Type.MONTHLY.equals(t.getType()))
                    .map(Price::getPrice).toList().stream().distinct().toList());
        });

        // get nearby places
        List<NearbyPlaceResponse> nearbyPlaceResponses = new ArrayList<>();
        Set<NearbyPlace> nearbyPlaces = building.getNearbyPlaces();
        if (!nearbyPlaces.isEmpty()) {
            for (NearbyPlace nearbyPlace : nearbyPlaces) {
                nearbyPlaceResponses.add(NearbyPlaceResponse.builder()
                        .id(nearbyPlace.getId())
                        .name(nearbyPlace.getName())
                        .distance(String.format("%.1f km", nearbyPlace.getDistance()))
                        .build());
            }
        }
        return SpaceResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .description(building.getDescription())
                .address(building.getAddress())
                .thumbnail(Helpers.setFileUrl(thumbnail))
                .facilities(facilityResponses)
                .types(types)
                .rating(review.getRating())
                .unit(buildingUnits.size())
                .price(price.stream().sorted().toList().get(0))
                .nearbyPlaces(nearbyPlaceResponses)
                .build();
    }

    public ResponseEntity<Object> getTypes() {
        List<DropdownResponse<String>> result = new ArrayList<>();
        Arrays.stream(Unit.Type.values()).forEach(type ->
                result.add(DropdownResponse.<String>builder().value(type.name()).label(type.label).build()));

        return ResponseUtil.ok("dropdown unit type", result);
    }
}
