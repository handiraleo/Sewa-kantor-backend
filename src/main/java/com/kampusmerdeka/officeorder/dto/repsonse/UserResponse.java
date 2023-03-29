package com.kampusmerdeka.officeorder.dto.repsonse;

import com.kampusmerdeka.officeorder.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private User.Role role;
    private String roleLabel;
    private String avatar;
}
