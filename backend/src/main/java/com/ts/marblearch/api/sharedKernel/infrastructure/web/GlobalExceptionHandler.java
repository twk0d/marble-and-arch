package com.ts.marblearch.api.sharedKernel.infrastructure.web;

import com.ts.marblearch.api.property.application.exceptions.PropertyNotFound;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PropertyNotFound.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleNotFound(PropertyNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseEnvelope.error("NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(BussinessRuleValidationException.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleBusinessRule(BussinessRuleValidationException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponseEnvelope.error("BUSINESS_RULE_VIOLATION", e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponseEnvelope.error("FORBIDDEN", "Você não tem permissão para acessar este recurso"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleAuthentication(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseEnvelope.error("UNAUTHORIZED", "Falha na autenticação: " + e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseEnvelope.error("BAD_REQUEST", "O parâmetro fornecido é inválido: " + e.getValue()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseEnvelope<Void>> handleGenericException(Exception e) {
        log.error("Unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseEnvelope.error("INTERNAL_ERROR", "Um erro inesperado ocorreu no servidor."));
    }
}
