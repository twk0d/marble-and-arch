package com.ts.marblearch.api.sharedKernel.domain.valuesObjects;

public record Description(String text) {
    public Description {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Description text cannot be null or blank.");
        }
    }
}
