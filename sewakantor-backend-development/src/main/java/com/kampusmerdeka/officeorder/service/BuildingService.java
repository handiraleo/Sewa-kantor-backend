package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.constant.FileDirectoryConstant;
import com.kampusmerdeka.officeorder.dto.repsonse.BuildingImageResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.BuildingResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.FacilityResponse;
import com.kampusmerdeka.officeorder.dto.request.BuildingRequest;
import com.kampusmerdeka.officeorder.entity.*;
import com.kampusmerdeka.officeorder.repository.*;
import com.kampusmerdeka.officeorder.util.FileDeleteUtil;
import com.kampusmerdeka.officeorder.util.FileUploadUtil;
import com.kampusmerdeka.officeorder.util.Helpers;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingImageRepository buildingImageRepository;
    @Autowired
    private ComplexRepository complexRepository;
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private BuildingFacilityRepository buildingFacilityRepository;

    public ResponseEntity<Object> getAll() {
        Iterable<Building> buildingIterable = buildingRepository.findAll();

        List<BuildingResponse> result = new ArrayList<>();
        buildingIterable.forEach(building -> {
            result.add(getResponse(building));
        });

        return ResponseUtil.ok("list building", result);
    }

    public ResponseEntity<Object> getOne(Long id) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isEmpty()) return ResponseUtil.notFound("building not found");

        BuildingResponse response = getResponse(buildingOptional.get());
        return ResponseUtil.ok("list building", response);
    }

    @Transactional
    public ResponseEntity<Object> createOne(BuildingRequest request) {
        Optional<Complex> complexOptional = complexRepository.findById(request.getComplexId());
        if (complexOptional.isEmpty()) return ResponseUtil.notFound("complex not found");

        Complex complex = complexOptional.get();
        Building building = Building.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .complex(complex)
                .build();

        building = buildingRepository.saveAndFlush(building);

        List<BuildingImage> buildingImages = new ArrayList<>();
        if (request.getImages() != null && request.getImages().size() > 0) {
            Building savedBuilding = building;
            request.getImages().forEach(file -> {
                if (file != null && !file.isEmpty()) {
                    try {
                        buildingImages.add(BuildingImage.builder()
                                .name(file.getOriginalFilename().replaceAll(" ", "-"))
                                .building(savedBuilding)
                                .path(FileUploadUtil.saveFile(
                                        FileDirectoryConstant.IMAGE_BUILDING_DIR,
                                        file.getOriginalFilename().replaceAll(" ", "-"),
                                        file)
                                )
                                .build()
                        );
                    } catch (IOException e) {
                        log.info("error save file {}", e.getLocalizedMessage());
                    }
                }
            });
        }

        List<BuildingFacility> buildingFacilities = new ArrayList<>();
        if (request.getFacilities() != null && request.getFacilities().size() > 0) {
            Building savedBuilding = building;
            request.getFacilities().forEach(facilityId -> {
                Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
                if (facilityOptional.isPresent()) {
                    Facility facility = facilityOptional.get();
                    buildingFacilities.add(BuildingFacility.builder().building(savedBuilding).facility(facility).build());
                }
            });
        }

        buildingImageRepository.saveAll(buildingImages);
        buildingFacilityRepository.saveAll(buildingFacilities);

        BuildingResponse response = getResponse(buildingRepository.saveAndFlush(building));

        return ResponseUtil.ok("building saved successfully", response);
    }

    public ResponseEntity<Object> updateOne(Long id, BuildingRequest request) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isEmpty()) return ResponseUtil.notFound("building not found");

        Optional<Complex> complexOptional = complexRepository.findById(request.getComplexId());
        if (complexOptional.isEmpty()) return ResponseUtil.notFound("complex not found");

        Complex complex = complexOptional.get();
        Building building = buildingOptional.get();

        Building buildingUpdate = Building.builder()
                .id(building.getId())
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .complex(complex)
                .build();

        BuildingResponse response = getResponse(buildingRepository.saveAndFlush(buildingUpdate));

        return ResponseUtil.ok("building updated successfully", response);
    }

    public ResponseEntity<Object> deleteOne(Long id) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isEmpty()) return ResponseUtil.notFound("building not found");

        Building building = buildingOptional.get();
        Long buildingId = building.getId();

        buildingRepository.deleteById(id);

        buildingImageRepository.findByBuilding_Id(buildingId).forEach(buildingImage -> {
            FileDeleteUtil.delete(buildingImage.getPath());
        });

        return ResponseUtil.ok("building deleted successfully");
    }

    private BuildingResponse getResponse(Building building) {
        List<BuildingImageResponse> images = new ArrayList<>();
        buildingImageRepository.findByBuilding(building).forEach(buildingImage -> {
            images.add(BuildingImageResponse.builder()
                    .id(buildingImage.getId())
                    .image(Helpers.setFileUrl(buildingImage.getPath()))
                    .build());
        });

        List<FacilityResponse> facilities = new ArrayList<>();
        buildingFacilityRepository.findByBuilding(building).forEach(buildingFacility -> {
            facilities.add(FacilityResponse.builder()
                    .id(buildingFacility.getFacility().getId())
                    .name(buildingFacility.getFacility().getName())
                    .build());
        });

        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .description(building.getDescription())
                .address(building.getAddress())
                .complexId(building.getComplex().getId())
                .complex(building.getComplex().getName())
                .images(images)
                .facilities(facilities)
                .build();
    }
}
