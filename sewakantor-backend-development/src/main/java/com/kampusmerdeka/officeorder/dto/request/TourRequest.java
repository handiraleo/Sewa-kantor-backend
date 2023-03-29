package com.kampusmerdeka.officeorder.dto.request;

import com.kampusmerdeka.officeorder.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourRequest {
    @NotNull(message = "building id is required")
    private Long buildingId;

    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    private String name;

    @Pattern(regexp = "^\\d+", message = "nomor telepon harus angka")
    @Size(min = 8, max = 15, message = "nomor telepon minimal 8 digit")
    @Size(max = 15, message = "nomor telepon maksimal 15 digit")
    private String phone;

    @NotNull(message = "company name is required")
    @NotBlank(message = "company name is required")
    private String companyName;

    @NotNull(message = "email must not be null")
    @NotBlank(message = "email name must not be blank")
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$", message = "please provide a valid email")
    private String companyEmail;

    @NotBlank(message = "start date is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$", message = "invalid start date format, ex: 2022-01-01")
    @Temporal(TemporalType.DATE)
    private String startDate;

    @NotBlank(message = "end date is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$", message = "invalid end date format, ex: 2022-01-01")
    @Temporal(TemporalType.DATE)
    private String endDate;

    @NotBlank(message = "time is required")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "invalid time format, ex: 13:30")
    private String time;

    @NotNull(message = "duration is required")
    private Long duration;

    @NotBlank(message = "unit type is required")
    @ValueOfEnum(enumClass = Unit.Type.class, message = "invalid unit type")
    private String unitType;
}
