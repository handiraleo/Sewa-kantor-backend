package com.kampusmerdeka.officeorder.controller.customer;

import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.dto.request.TourRequest;
import com.kampusmerdeka.officeorder.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/customer/tour")
public class TourController {
    @Autowired
    private TourService tourService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody TourRequest request) {
        return tourService.create(request);
    }
}
