package com.ts.marblearch.api.webAdapter.property;

import com.ts.marblearch.api.property.application.exceptions.PropertyNotFound;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class PropertyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(PropertyExceptionHandler.class);

    @ExceptionHandler(PropertyNotFound.class)
    public ResponseEntity<String> propertyNotFound(PropertyNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BussinessRuleValidationException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRule(BussinessRuleValidationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Business Rule Violation");
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(org.springframework.security.access.AccessDeniedException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Forbidden");
        error.put("message", "Access Denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        logger.error("Unexpected error occurred", e);
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", "An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
