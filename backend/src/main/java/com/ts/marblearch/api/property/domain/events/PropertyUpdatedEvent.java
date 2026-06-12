package com.ts.marblearch.api.property.domain.events;

import com.ts.marblearch.api.property.domain.entity.property.Property;

import java.util.concurrent.CompletableFuture;

public record PropertyUpdatedEvent(Property property, CompletableFuture<Void> future) {

    @Override
    public CompletableFuture<Void> future() {
        return future;
    }
}