package com.openclassrooms.mddapi.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String details;
    private LocalDateTime timestamp;
    @Setter
    private Map<String, String> validationErrors;

    public ErrorResponse(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

}

