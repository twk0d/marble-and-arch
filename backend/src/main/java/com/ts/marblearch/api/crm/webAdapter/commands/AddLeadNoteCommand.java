package com.ts.marblearch.api.crm.webAdapter.commands;

import com.ts.marblearch.api.crm.webAdapter.requests.AddLeadNoteRequest;
import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class AddLeadNoteCommand extends BaseCommand<Void> {
    private final AddLeadNoteRequest request;

    public AddLeadNoteCommand(AddLeadNoteRequest request, CompletableFuture<Void> resultFuture) {
        super(request.getLeadId(), resultFuture);
        this.request = request;
    }
}
