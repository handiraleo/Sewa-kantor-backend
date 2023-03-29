package com.kampusmerdeka.officeorder.controller.customer;

import com.kampusmerdeka.officeorder.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/customer/spaces")
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return spaceService.getAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return spaceService.getOne(id);
    }

    @GetMapping(value = "types")
    public ResponseEntity<Object> getTypes() {
        return spaceService.getTypes();
    }
}
