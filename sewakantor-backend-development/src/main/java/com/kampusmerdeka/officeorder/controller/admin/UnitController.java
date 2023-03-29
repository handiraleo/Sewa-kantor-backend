package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.UnitRequest;
import com.kampusmerdeka.officeorder.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/units")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return unitService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute UnitRequest request) {
        return unitService.createOne(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute UnitRequest request) {
        return unitService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return unitService.deleteOne(id);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return unitService.getOne(id);
    }
}
