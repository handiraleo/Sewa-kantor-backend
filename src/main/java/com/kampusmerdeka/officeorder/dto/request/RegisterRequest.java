package com.kampusmerdeka.officeorder.dto.request;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "first name must not be null")
    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @Nullable
    private String lastName;

    @NotNull(message = "email must not be null")
    @NotBlank(message = "email name must not be blank")
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$", message = "please provide a valid email")
    private String email;

    @NotNull(message = "password must not be null")
    @NotBlank(message = "password must not be blank")
    @Size(min = 8, message = "password must contains at least 8 character")
    private String password;
}
