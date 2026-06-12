package com.ts.marblearch.api.sharedKernel.domain.exceptions;

public class BussinessRuleValidationException extends RuntimeException {
    public BussinessRuleValidationException(String message) {
        super(message);
    }
}
