package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateOthersRequest(
    String typeDescription,
    java.math.BigDecimal areaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit areaUnit,
    int yearBuilt,
    String legalStatus,
    String description
) implements UpdateDetailsRequest {}
