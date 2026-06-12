package com.ts.marblearch.api.scheduling.webAdapter.commands;

import com.ts.marblearch.api.scheduling.webAdapter.requests.ScheduleVisitRequest;
import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class ScheduleVisitCommand extends BaseCommand<UUID> {
    private final ScheduleVisitRequest request;

    public ScheduleVisitCommand(ScheduleVisitRequest request, CompletableFuture<UUID> resultFuture) {
        super(request.getClientId(), resultFuture);
        this.request = request;
    }
}
