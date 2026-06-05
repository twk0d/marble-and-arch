package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdateOfficeRequest(
    java.math.BigDecimal areaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit areaUnit,
    int numberOfRooms,
    int bathrooms,
    int parkingSpaces,
    int floor,
    String buildingName,
    boolean hasReception,
    boolean hasAirConditioning
) implements UpdateDetailsRequest {}

