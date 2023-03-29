package com.kampusmerdeka.officeorder.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8396470204330633005L;

    private Long timestamp;

    private int code;

    private String status;

    private String message;

    private T data;

    private T errors;

    private T paging;
}