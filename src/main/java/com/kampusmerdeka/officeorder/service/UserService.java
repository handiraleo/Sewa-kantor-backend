package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.DropdownResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.UserResponse;
import com.kampusmerdeka.officeorder.dto.request.UserRequest;
import com.kampusmerdeka.officeorder.dto.request.UserUpdateRequest;
import com.kampusmerdeka.officeorder.entity.User;
import com.kampusmerdeka.officeorder.entity.UserAdmin;
import com.kampusmerdeka.officeorder.repository.UserRepository;
import com.kampusmerdeka.officeorder.util.FileDeleteUtil;
import com.kampusmerdeka.officeorder.util.FileUploadUtil;
import com.kampusmerdeka.officeorder.util.Helpers;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.kampusmerdeka.officeorder.constant.FileDirectoryConstant.IMAGE_USER_DIR;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    public ResponseEntity<Object> createOne(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            return ResponseUtil.badRequest("username already exist");

        UserAdmin userAdmin = UserAdmin.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .role(User.Role.valueOf(request.getRole()))
                .password(passwordEncoder.encode(request.getPassword()))
                .avatar(request.getAvatar() == null
                        ? null
                        : FileUploadUtil.saveFile(IMAGE_USER_DIR, request.getAvatar().getOriginalFilename(), request.getAvatar()))
                .build();

        userAdmin = userRepository.saveAndFlush(userAdmin);

        return ResponseUtil.ok("new user created successfully",
                UserResponse.builder()
                        .id(userAdmin.getId())
                        .username(userAdmin.getUsername())
                        .firstName(userAdmin.getFirstName())
                        .lastName(userAdmin.getLastName())
                        .role(userAdmin.getRole())
                        .roleLabel(userAdmin.getRole().name())
                        .avatar(Helpers.setFileUrl(userAdmin.getAvatar()))
                        .build()
        );
    }

    public ResponseEntity<Object> getAll() {
        List<UserResponse> result = new ArrayList<>();
        userRepository.findAll().forEach(userAdmin -> result.add(
                UserResponse.builder()
                        .id(userAdmin.getId())
                        .username(userAdmin.getUsername())
                        .firstName(userAdmin.getFirstName())
                        .lastName(userAdmin.getLastName())
                        .role(userAdmin.getRole())
                        .roleLabel(userAdmin.getRole().name())
                        .avatar(Helpers.setFileUrl(userAdmin.getAvatar()))
                        .build()
        ));

        return ResponseUtil.ok("list users", result);
    }

    public ResponseEntity<Object> getOne(Long id) {
        Optional<UserAdmin> userAdminOptional = userRepository.findById(id);
        if (userAdminOptional.isEmpty()) return ResponseUtil.notFound("user not found");

        UserAdmin userAdmin = userAdminOptional.get();

        return ResponseUtil.ok("user by id", UserResponse.builder()
                .id(userAdmin.getId())
                .username(userAdmin.getUsername())
                .firstName(userAdmin.getFirstName())
                .lastName(userAdmin.getLastName())
                .role(userAdmin.getRole())
                .roleLabel(userAdmin.getRole().name())
                .avatar(Helpers.setFileUrl(userAdmin.getAvatar()))
                .build());
    }

    @SneakyThrows
    public ResponseEntity<Object> updateOne(Long id, UserUpdateRequest request) {
        Optional<UserAdmin> userAdminOptional = userRepository.findById(id);
        if (userAdminOptional.isEmpty()) return ResponseUtil.notFound("user not found");

        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), id))
            return ResponseUtil.badRequest("username already exist");

        UserAdmin userAdmin = userAdminOptional.get();
        userAdmin = userAdmin.toBuilder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(User.Role.valueOf(request.getRole()))
                .password(passwordEncoder.encode(request.getPassword()))
                .avatar(request.getAvatar() == null
                        ? userAdmin.getAvatar()
                        : FileUploadUtil.saveFile(IMAGE_USER_DIR, request.getAvatar().getOriginalFilename(), request.getAvatar()))
                .build();

        userAdmin = userRepository.saveAndFlush(userAdmin);

        return ResponseUtil.ok("user updated successfully",
                UserResponse.builder()
                        .id(userAdmin.getId())
                        .username(userAdmin.getUsername())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .role(userAdmin.getRole())
                        .roleLabel(userAdmin.getRole().name())
                        .avatar(Helpers.setFileUrl(userAdmin.getAvatar()))
                        .build()
        );
    }

    public ResponseEntity<Object> deletetOne(Long id) {
        Optional<UserAdmin> userAdminOptional = userRepository.findById(id);
        if (userAdminOptional.isEmpty()) return ResponseUtil.notFound("user not found");

        UserAdmin userAdmin = userAdminOptional.get();

        userRepository.deleteById(id);

        FileDeleteUtil.delete(userAdmin.getAvatar());
        return ResponseUtil.ok("user deleted successfully");
    }

    public ResponseEntity<Object> getRoles() {
        List<DropdownResponse<User.Role>> result = new ArrayList<>();
        Arrays.stream(User.Role.values()).forEach(role -> result.add(new DropdownResponse<>(role, role.name())));

        return ResponseUtil.ok("user roles dropdown", result);
    }
}
