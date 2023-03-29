package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.UserRequest;
import com.kampusmerdeka.officeorder.dto.request.UserUpdateRequest;
import com.kampusmerdeka.officeorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/admin/users")
@Validated
@PreAuthorize("hasRole('SUPERADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createOne(@Valid @ModelAttribute UserRequest request) {
        return userService.createOne(request);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return userService.getOne(id);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateOne(@PathVariable(name = "id") Long id, @Valid @ModelAttribute UserUpdateRequest request) {
        return userService.updateOne(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable(name = "id") Long id) {
        return userService.deletetOne(id);
    }

    @GetMapping(value = "roles")
    public ResponseEntity<Object> roles() {
        return userService.getRoles();
    }
}
