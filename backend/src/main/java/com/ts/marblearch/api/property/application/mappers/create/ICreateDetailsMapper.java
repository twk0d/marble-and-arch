package com.ts.marblearch.api.property.application.mappers.create;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreateDetailsRequest;

public interface ICreateDetailsMapper<R extends CreateDetailsRequest, D> {
    D toDomain(R request);
    PropertyType getSupportedType();
}
