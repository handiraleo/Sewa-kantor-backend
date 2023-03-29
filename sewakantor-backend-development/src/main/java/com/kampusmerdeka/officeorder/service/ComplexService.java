package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.constant.FileDirectoryConstant;
import com.kampusmerdeka.officeorder.dto.repsonse.ComplexResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.DropdownResponse;
import com.kampusmerdeka.officeorder.dto.request.ComplexRequest;
import com.kampusmerdeka.officeorder.entity.City;
import com.kampusmerdeka.officeorder.entity.Complex;
import com.kampusmerdeka.officeorder.repository.CityRepository;
import com.kampusmerdeka.officeorder.repository.ComplexRepository;
import com.kampusmerdeka.officeorder.util.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplexService {
    @Autowired
    private ComplexRepository complexRepository;
    @Autowired
    private CityRepository cityRepository;

    public ResponseEntity<Object> getAll() {
        Iterable<Complex> complexIterable = complexRepository.findAll();

        List<ComplexResponse> result = new ArrayList<>();
        complexIterable.forEach(complex -> {
            result.add(getResponse(complex));
        });

        return ResponseUtil.ok("list complex", result);
    }

    public ResponseEntity<Object> getOne(Long id) {
        Optional<Complex> complexOptional = complexRepository.findById(id);
        if (complexOptional.isEmpty()) return ResponseUtil.notFound("complex not found");

        ComplexResponse response = getResponse(complexOptional.get());
        return ResponseUtil.ok("list complex", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> createOne(ComplexRequest request) {
        Optional<City> cityOptional = cityRepository.findById(request.getCityId());
        if (cityOptional.isEmpty()) return ResponseUtil.notFound("city not found");

        City city = cityOptional.get();
        Complex complex = Complex.builder()
                .name(request.getName())
                .description(request.getDescription())
                .city(city)
                .imagePath((request.getImage() != null && !request.getImage().isEmpty())
                        ? FileUploadUtil.saveFile(
                        FileDirectoryConstant.IMAGE_COMPLEX_DIR,
                        request.getImage().getOriginalFilename().replaceAll(" ", "-"),
                        request.getImage())
                        : null)
                .build();

        ComplexResponse response = getResponse(complexRepository.saveAndFlush(complex));

        return ResponseUtil.ok("complex saved successfully", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> updateOne(Long id, ComplexRequest request) {
        Optional<Complex> complexOptional = complexRepository.findById(id);
        if (complexOptional.isEmpty()) return ResponseUtil.notFound("complex not found");

        Optional<City> cityOptional = cityRepository.findById(request.getCityId());
        if (cityOptional.isEmpty()) return ResponseUtil.notFound("city not found");

        City city = cityOptional.get();
        Complex complex = complexOptional.get();

        Complex complexUpdate = Complex.builder()
                .id(complex.getId())
                .name(request.getName())
                .description(request.getDescription())
                .city(city)
                .imagePath((request.getImage() != null && !request.getImage().isEmpty())
                        ? FileUploadUtil.saveFile(
                        FileDirectoryConstant.IMAGE_COMPLEX_DIR,
                        request.getImage().getOriginalFilename().replaceAll(" ", "-"),
                        request.getImage())
                        : complex.getImagePath())
                .build();

        ComplexResponse response = getResponse(complexRepository.saveAndFlush(complexUpdate));

        return ResponseUtil.ok("complex updated successfully", response);
    }

    public ResponseEntity<Object> deleteOne(Long id) {
        Optional<Complex> complexOptional = complexRepository.findById(id);
        if (complexOptional.isEmpty()) return ResponseUtil.notFound("complex not found");

        Complex complex = complexOptional.get();
        String imagePath = complex.getImagePath();

        complexRepository.deleteById(id);
        FileDeleteUtil.delete(imagePath);

        return ResponseUtil.ok("complex deleted successfully");
    }

    private ComplexResponse getResponse(Complex complex) {
        return ComplexResponse.builder()
                .id(complex.getId())
                .name(complex.getName())
                .description(complex.getDescription())
                .image(Helpers.setFileUrl(complex.getImagePath()))
                .cityId(complex.getCity().getId())
                .city(complex.getCity().getName())
                .build();
    }

    public ResponseEntity<Object> dropdown() {
        List<DropdownResponse> result = new ArrayList<>();
        complexRepository.findAll().forEach(complex ->
                result.add(DropdownResponse.builder()
                        .value(complex.getId())
                        .label(complex.getName())
                        .build())
        );

        return ResponseUtil.ok("dropdown complex", result);

    }
}
