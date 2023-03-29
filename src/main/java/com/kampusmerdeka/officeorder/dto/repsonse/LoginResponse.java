package com.kampusmerdeka.officeorder.dto.repsonse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String username;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private Integer roleId;
    private String role;
    private String tokenType;
    private String token;
}