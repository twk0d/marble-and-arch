package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdatePentHouseRequest(
    int bedrooms,
    int suites,
    int bathrooms,
    int parkingSpaces,
    java.math.BigDecimal totalAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit totalAreaUnit,
    java.math.BigDecimal builtAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit builtAreaUnit,
    java.math.BigDecimal terraceAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit terraceAreaUnit,
    String viewDescription,
    boolean hasGarage,
    boolean hasPool,
    boolean hasPrivateElevator
) implements UpdateDetailsRequest {}

