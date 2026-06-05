package com.ts.marblearch.api.webAdapter.property.responses;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import java.math.BigDecimal;
import java.util.UUID;

public record PropertySummaryDTO(
    UUID id,
    PropertyType type,
    String city,
    String state,
    BigDecimal priceAmount,
    String priceCurrency,
    String mainImageUrl,
    boolean active
) {}
