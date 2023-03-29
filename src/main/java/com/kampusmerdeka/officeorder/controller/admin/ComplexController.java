package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.ComplexRequest;
import com.kampusmerdeka.officeorder.service.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/complexes")
public class ComplexController {
    @Autowired
    private ComplexService complexService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return complexService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute ComplexRequest request) {
        return complexService.createOne(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute ComplexRequest request) {
        return complexService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return complexService.deleteOne(id);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return complexService.getOne(id);
    }

    @GetMapping(value = "dropdown")
    public ResponseEntity<Object> dropdown() {
        return complexService.dropdown();
    }
}
