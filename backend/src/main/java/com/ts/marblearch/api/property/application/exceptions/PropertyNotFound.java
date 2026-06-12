package com.ts.marblearch.api.property.application.exceptions;

import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;

public class PropertyNotFound extends BussinessRuleValidationException {
    public PropertyNotFound(String message) {
        super(message);
    }
}
