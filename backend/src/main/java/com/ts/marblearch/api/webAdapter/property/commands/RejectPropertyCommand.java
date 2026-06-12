package com.ts.marblearch.api.webAdapter.property.commands;

import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RejectPropertyCommand extends BaseCommand<Void> {
    public RejectPropertyCommand(UUID propertyId, CompletableFuture<Void> resultFuture) {
        super(propertyId, resultFuture);
    }

    public UUID getAggregateId() {
        return (UUID) getSource();
    }
}
