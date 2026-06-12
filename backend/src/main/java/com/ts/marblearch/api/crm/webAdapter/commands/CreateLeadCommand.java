package com.ts.marblearch.api.crm.webAdapter.commands;

import com.ts.marblearch.api.crm.webAdapter.requests.CreateLeadRequest;
import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class CreateLeadCommand extends BaseCommand<UUID> {
    private final CreateLeadRequest request;

    public CreateLeadCommand(CreateLeadRequest request, CompletableFuture<UUID> resultFuture) {
        super(request.getEmail(), resultFuture);
        this.request = request;
    }
}
