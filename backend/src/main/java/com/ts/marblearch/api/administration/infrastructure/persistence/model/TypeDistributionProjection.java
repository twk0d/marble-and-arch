package com.ts.marblearch.api.administration.infrastructure.persistence.model;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;

public interface TypeDistributionProjection {
    PropertyType getType();
    long getCount();
}
