package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.constant.FileDirectoryConstant;
import com.kampusmerdeka.officeorder.dto.repsonse.DropdownResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.FacilityResponse;
import com.kampusmerdeka.officeorder.dto.request.FacilityRequest;
import com.kampusmerdeka.officeorder.entity.Facility;
import com.kampusmerdeka.officeorder.repository.FacilityRepository;
import com.kampusmerdeka.officeorder.util.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    public ResponseEntity<Object> getAll() {
        Iterable<Facility> facilityIterable = facilityRepository.findAll();

        List<FacilityResponse> result = new ArrayList<>();
        facilityIterable.forEach(facility -> result.add(getResponse(facility)));

        return ResponseUtil.ok("list facility", result);
    }

    public ResponseEntity<Object> getOne(Long id) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if (facilityOptional.isEmpty()) return ResponseUtil.notFound("facility not found");

        FacilityResponse response = getResponse(facilityOptional.get());
        return ResponseUtil.ok("list facility", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> createOne(FacilityRequest request) {
        Facility facility = Facility.builder()
                .name(request.getName())
                .icon((request.getIcon() != null && !request.getIcon().isEmpty())
                        ? FileUploadUtil.saveFile(
                        FileDirectoryConstant.IMAGE_FACILITY_DIR,
                        request.getIcon().getOriginalFilename().replaceAll(" ", "-"),
                        request.getIcon())
                        : null)
                .build();

        FacilityResponse response = getResponse(facilityRepository.saveAndFlush(facility));

        return ResponseUtil.ok("facility saved successfully", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> updateOne(Long id, FacilityRequest request) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if (facilityOptional.isEmpty()) return ResponseUtil.notFound("facility not found");

        Facility facility = facilityOptional.get();

        Facility facilityUpdate = Facility.builder()
                .id(facility.getId())
                .name(request.getName())
                .icon((request.getIcon() != null && !request.getIcon().isEmpty())
                        ? FileUploadUtil.saveFile(
                        FileDirectoryConstant.IMAGE_FACILITY_DIR,
                        request.getIcon().getOriginalFilename().replaceAll(" ", "-"),
                        request.getIcon())
                        : facility.getIcon())
                .build();

        FacilityResponse response = getResponse(facilityRepository.saveAndFlush(facilityUpdate));

        return ResponseUtil.ok("facility updated successfully", response);
    }

    public ResponseEntity<Object> deleteOne(Long id) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if (facilityOptional.isEmpty()) return ResponseUtil.notFound("facility not found");

        Facility facility = facilityOptional.get();
        String icon = facility.getIcon();

        facilityRepository.deleteById(id);
        FileDeleteUtil.delete(icon);

        return ResponseUtil.ok("facility deleted successfully");
    }

    private FacilityResponse getResponse(Facility facility) {
        return FacilityResponse.builder()
                .id(facility.getId())
                .name(facility.getName())
                .icon(Helpers.setFileUrl(facility.getIcon()))
                .build();
    }

    public ResponseEntity<Object> dropdown() {
        List<DropdownResponse> result = new ArrayList<>();
        facilityRepository.findAll().forEach(complex ->
                result.add(DropdownResponse.builder()
                        .value(complex.getId())
                        .label(complex.getName())
                        .build())
        );

        return ResponseUtil.ok("dropdown facility", result);

    }
}
