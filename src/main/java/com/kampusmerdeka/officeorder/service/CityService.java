package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.CityResponse;
import com.kampusmerdeka.officeorder.dto.request.CityRequest;
import com.kampusmerdeka.officeorder.entity.City;
import com.kampusmerdeka.officeorder.repository.CityRepository;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public ResponseEntity<Object> getAll() {
        Iterable<City> cityIterable = cityRepository.findAll();

        List<CityResponse> result = new ArrayList<>();
        cityIterable.forEach(city -> result.add(getResponse(city)));

        return ResponseUtil.ok("list city", result);
    }

    public ResponseEntity<Object> getOne(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isEmpty()) return ResponseUtil.notFound("city not found");

        CityResponse response = getResponse(cityOptional.get());
        return ResponseUtil.ok("list city", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> createOne(CityRequest request) {
        City city = City.builder().name(request.getName()).build();

        CityResponse response = getResponse(cityRepository.saveAndFlush(city));

        return ResponseUtil.ok("city saved successfully", response);
    }

    @SneakyThrows
    public ResponseEntity<Object> updateOne(Long id, CityRequest request) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isEmpty()) return ResponseUtil.notFound("city not found");

        City city = cityOptional.get();

        City cityUpdate = City.builder()
                .id(city.getId())
                .name(request.getName())
                .build();

        CityResponse response = getResponse(cityRepository.saveAndFlush(cityUpdate));

        return ResponseUtil.ok("city updated successfully", response);
    }

    public ResponseEntity<Object> deleteOne(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isEmpty()) return ResponseUtil.notFound("city not found");


        cityRepository.deleteById(id);

        return ResponseUtil.ok("city deleted successfully");
    }

    private CityResponse getResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
