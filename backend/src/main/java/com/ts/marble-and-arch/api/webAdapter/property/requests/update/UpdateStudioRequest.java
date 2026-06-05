package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateStudioRequest(
    java.math.BigDecimal areaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit areaUnit,
    int bathroom,
    int floor,
    java.math.BigDecimal condominiumFeeAmount,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency condominiumFeeCurrency,
    boolean hasBalcony,
    boolean isFurnished
) implements UpdateDetailsRequest {}

