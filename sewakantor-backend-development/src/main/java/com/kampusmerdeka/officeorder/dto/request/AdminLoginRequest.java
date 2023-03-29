package com.kampusmerdeka.officeorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginRequest extends LoginRequest {
    @NotNull(message = "username must not be null")
    @NotBlank(message = "username name must not be blank")
    private String username;
}
