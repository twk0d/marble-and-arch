package com.ts.marblearch.api.crm.webAdapter;

import com.ts.marblearch.api.crm.webAdapter.commands.AddLeadNoteCommand;
import com.ts.marblearch.api.crm.webAdapter.commands.CreateLeadCommand;
import com.ts.marblearch.api.crm.webAdapter.requests.AddLeadNoteRequest;
import com.ts.marblearch.api.crm.webAdapter.requests.CreateLeadRequest;
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
@RequestMapping("/api/${api.version}/leads")
@RequiredArgsConstructor
public class CRMController {

    private final IModuleClient moduleClient;

    @PostMapping
    public CompletableFuture<ResponseEntity<UUID>> createLead(@RequestBody CreateLeadRequest request) {
        CompletableFuture<UUID> resultFuture = new CompletableFuture<>();
        var command = new CreateLeadCommand(request, resultFuture);
        return moduleClient.executeCommandAsync(command)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/notes")
    public CompletableFuture<ResponseEntity<Void>> addLeadNote(@RequestBody AddLeadNoteRequest request) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new AddLeadNoteCommand(request, resultFuture);
        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }
}
