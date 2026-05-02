package com.ts.marblearch.api.sharedKernel.application.events;

import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import com.ts.marblearch.api.sharedKernel.application.events.integration.BaseIntegrationEvent;
import com.ts.marblearch.api.sharedKernel.application.events.query.BaseQuery;

import java.util.concurrent.CompletableFuture;

public interface IModuleClient {
    <TResult> CompletableFuture<TResult> executeCommandAsync(BaseCommand<TResult> command);

    <TResult> CompletableFuture<TResult> executeQueryAsync(BaseQuery<TResult> query);

    <TResult> CompletableFuture<TResult> executeIntegrationEventAsync(BaseIntegrationEvent<TResult> integrationEvent);
}
