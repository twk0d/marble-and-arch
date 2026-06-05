package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateCondominiumHouseRequest(
    int bedrooms,
    int suites,
    int bathrooms,
    int parkingSpaces,
    int floor,
    java.math.BigDecimal totalAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit totalAreaUnit,
    java.math.BigDecimal builtAreaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit builtAreaUnit,
    String condominiumName,
    java.math.BigDecimal condominiumFeeAmount,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency condominiumFeeCurrency,
    java.util.Set<com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Amenity> amenities,
    boolean hasGarage,
    boolean hasBalcony
) implements UpdateDetailsRequest {}

