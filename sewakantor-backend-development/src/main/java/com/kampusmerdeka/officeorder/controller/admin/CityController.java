package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.CityRequest;
import com.kampusmerdeka.officeorder.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return cityService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute CityRequest request) {
        return cityService.createOne(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute CityRequest request) {
        return cityService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return cityService.deleteOne(id);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return cityService.getOne(id);
    }
}
