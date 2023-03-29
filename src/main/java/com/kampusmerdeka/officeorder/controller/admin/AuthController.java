package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.AdminLoginRequest;
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
@RequestMapping(value = "/v1/admin/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody AdminLoginRequest request) {
        return authService.login(request);
    }
}
