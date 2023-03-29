package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.BuildingRequest;
import com.kampusmerdeka.officeorder.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/buildings")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return buildingService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute BuildingRequest request) {
        return buildingService.createOne(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute BuildingRequest request) {
        return buildingService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return buildingService.deleteOne(id);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return buildingService.getOne(id);
    }
}
