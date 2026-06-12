package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateCountryHouseRequest(
    int bedrooms,
    int suites,
    int bathrooms,
    java.math.BigDecimal totalAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit totalAreaUnit,
    java.math.BigDecimal builtAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit builtAreaUnit,
    String description,
    boolean hasGarage,
    boolean hasPool,
    boolean hasRiverOrLake
) implements UpdateDetailsRequest {}

