package com.ts.marblearch.api.administration.infrastructure.persistence.model;

import java.math.BigDecimal;

public record GeneralStatsProjection(
    Long total,
    Long active,
    BigDecimal totalValue
) {}
