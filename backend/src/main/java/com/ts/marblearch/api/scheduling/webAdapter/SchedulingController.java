package com.ts.marblearch.api.scheduling.webAdapter;

import com.ts.marblearch.api.scheduling.webAdapter.commands.ScheduleVisitCommand;
import com.ts.marblearch.api.scheduling.webAdapter.requests.ScheduleVisitRequest;
import com.ts.marblearch.api.sharedKernel.application.events.IModuleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/${api.version}/scheduling/visits")
@RequiredArgsConstructor
public class SchedulingController {

    private final IModuleClient moduleClient;

    @PostMapping
    public CompletableFuture<ResponseEntity<UUID>> scheduleVisit(@RequestBody ScheduleVisitRequest request) {
        CompletableFuture<UUID> resultFuture = new CompletableFuture<>();
        var command = new ScheduleVisitCommand(request, resultFuture);
        return moduleClient.executeCommandAsync(command)
                .thenApply(ResponseEntity::ok);
    }
}
