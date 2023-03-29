package com.kampusmerdeka.officeorder.dto.repsonse;

import com.kampusmerdeka.officeorder.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {
    private Long id;
    private Long buildingId;
    private String building;
    private String name;
    private String phone;
    private String companyName;
    private String companyEmail;
    private String startDate;
    private String endDate;
    private String time;
    private Long duration;
    private Unit.Type unitType;
}
