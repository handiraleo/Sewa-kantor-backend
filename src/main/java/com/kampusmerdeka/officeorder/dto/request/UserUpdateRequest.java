package com.kampusmerdeka.officeorder.dto.request;

import com.kampusmerdeka.officeorder.entity.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {
    @NotNull(message = "first name requuired")
    @NotBlank(message = "first name requuired")
    private String firstName;

    @NotNull(message = "last name requuired")
    @NotBlank(message = "last name requuired")
    private String lastName;

    @NotNull(message = "username is requuired")
    @NotBlank(message = "username is requuired")
    private String username;

    @NotNull(message = "role is requuired")
    @NotBlank(message = "role is requuired")
    @ValueOfEnum(enumClass = User.Role.class, message = "role not valid")
    private String role;

    private String password;

    private MultipartFile avatar;
}
