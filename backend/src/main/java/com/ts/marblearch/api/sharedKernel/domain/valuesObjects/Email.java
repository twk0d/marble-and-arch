package com.ts.marblearch.api.sharedKernel.domain.valuesObjects;

import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;

import java.util.regex.Pattern;

public record Email(String address) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email {
        if (address == null || address.isBlank()) {
            throw new BussinessRuleValidationException("Email address cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new BussinessRuleValidationException("Invalid email format");
        }
    }

    @Override
    public String toString() {
        return address;
    }
}
