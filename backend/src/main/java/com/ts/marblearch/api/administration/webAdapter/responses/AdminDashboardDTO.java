package com.ts.marblearch.api.administration.webAdapter.responses;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import java.math.BigDecimal;
import java.util.Map;

public record AdminDashboardDTO(
    long totalProperties,
    long activeProperties,
    long inactiveProperties,
    BigDecimal totalMarketValue,
    Map<PropertyType, Long> distributionByType,
    Map<String, Long> distributionByCity,
    long recentActivitiesCount
) {}
