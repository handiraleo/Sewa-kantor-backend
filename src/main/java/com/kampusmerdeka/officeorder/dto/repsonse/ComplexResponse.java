package com.kampusmerdeka.officeorder.dto.repsonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long cityId;
    private String city;
}
