package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateCondominiumPlotRequest(
    java.math.BigDecimal areaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit areaUnit,
    String dimensions,
    String topography,
    String condominiumName,
    java.math.BigDecimal condominiumFeeAmount,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency condominiumFeeCurrency,
    java.util.Set<com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Amenity> amenities
) implements UpdateDetailsRequest {}

