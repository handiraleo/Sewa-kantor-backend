package com.kampusmerdeka.officeorder.controller.customer;

import com.kampusmerdeka.officeorder.dto.request.CustomerLoginRequest;
import com.kampusmerdeka.officeorder.dto.request.RegisterRequest;
import com.kampusmerdeka.officeorder.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/v1/customer/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody CustomerLoginRequest request) {
        return authService.login(request);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
