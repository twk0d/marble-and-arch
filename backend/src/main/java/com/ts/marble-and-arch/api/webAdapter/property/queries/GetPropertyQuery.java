package com.ts.marblearch.api.webAdapter.property.queries;

import com.ts.marblearch.api.sharedKernel.application.events.query.BaseQuery;
import com.ts.marblearch.api.webAdapter.property.responses.PropertyDTO;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class GetPropertyQuery extends BaseQuery<PropertyDTO> {
    private final UUID propertyId;

    public GetPropertyQuery(UUID propertyId, CompletableFuture<PropertyDTO> resultFuture) {
        super(GetPropertyQuery.class, resultFuture);
        this.propertyId = propertyId;
    }
}
