package com.ts.marblearch.api.property.application.mappers.update;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.webAdapter.property.requests.update.UpdateDetailsRequest;


public interface IUpdateDetailsMapper<R extends UpdateDetailsRequest, D> {
    D toDomain(R request);
    PropertyType getSupportedType();
}
