package com.alibou.common.exception;

import feign.FeignException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        // Return a structured error response
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        // Option 1: Simply extract the error message from the exception.
        String errorMessage = ex.contentUTF8();  // if the body is present; adjust as needed
        // Option 2: If your custom error decoder wraps the message into a custom exception,
        // then you can use ex.getMessage() or cast to your custom exception.

        // Build an error response DTO (you could define an ErrorResponse class)
        ErrorResponse error = new ErrorResponse(errorMessage);
        // Return a ResponseEntity with an appropriate status.
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.status()));
    }

    // Define a simple ErrorResponse class
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse {
        private String message;
    }
}
