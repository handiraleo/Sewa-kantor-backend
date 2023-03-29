package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.FacilityRequest;
import com.kampusmerdeka.officeorder.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/facilities")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return facilityService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute FacilityRequest request) {
        return facilityService.createOne(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute FacilityRequest request) {
        return facilityService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return facilityService.deleteOne(id);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return facilityService.getOne(id);
    }

    @GetMapping(value = "dropdown")
    public ResponseEntity<Object> dropdown() {
        return facilityService.dropdown();
    }
}
