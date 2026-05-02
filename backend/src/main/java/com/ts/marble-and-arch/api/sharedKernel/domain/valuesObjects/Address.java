package com.ts.marblearch.api.sharedKernel.domain.valuesObjects;

import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;

public record Address(
    String street,
    String number,
    String neighborhood,
    String city,
    String state,
    String postalCode,
    String complement
) {
    public Address {
        if (street == null || street.isBlank()) throw new BussinessRuleValidationException("Street is required");
        if (city == null || city.isBlank()) throw new BussinessRuleValidationException("City is required");
        if (state == null || state.isBlank()) throw new BussinessRuleValidationException("State is required");
        if (postalCode == null || postalCode.isBlank()) throw new BussinessRuleValidationException("Postal code is required");
    }
}
