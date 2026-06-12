package com.ts.marblearch.api.webAdapter.property.requests.update;

public record UpdatePlotRequest(
    java.math.BigDecimal areaValue,
    com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit areaUnit,
    String dimensions,
    String topographyDescription,
    String zoning,
    boolean hasFence
) implements UpdateDetailsRequest {}

