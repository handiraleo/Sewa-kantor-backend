package com.kampusmerdeka.officeorder.exception;

import com.kampusmerdeka.officeorder.dto.common.RequestValidationError;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import lombok.NonNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatus status,
                                                                  @NonNull final WebRequest request) {

        List<RequestValidationError> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(RequestValidationError.builder()
                    .field(((FieldError) error).getField())
                    .error(error.getDefaultMessage())
                    .build());
        }

        return ResponseUtil.error(errors.get(0).getError(), status, errors);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        List<RequestValidationError> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(RequestValidationError.builder()
                    .field(((FieldError) error).getField())
                    .error(error.getDefaultMessage())
                    .build());
        }

        return ResponseUtil.error(errors.get(0).getError(), status, errors);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<RequestValidationError> errors = new ArrayList<>();

        errors.add(RequestValidationError.builder()
                .field(ex.getPropertyName())
                .error(ex.getLocalizedMessage())
                .build());

        return ResponseUtil.error(errors.get(0).getError(), HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        List<RequestValidationError> errors = new ArrayList<>();
        if (!ex.getConstraintViolations().isEmpty()) {
            for (ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
                String fieldName = null;
                for (Path.Node node : constraintViolation.getPropertyPath()) {
                    fieldName = node.getName();
                }
                errors.add(RequestValidationError.builder()
                        .field(fieldName)
                        .error(constraintViolation.getMessage()).build());
            }
        }

        return ResponseUtil.error(errors.get(0).getError(), HttpStatus.BAD_REQUEST, errors);
    }
}