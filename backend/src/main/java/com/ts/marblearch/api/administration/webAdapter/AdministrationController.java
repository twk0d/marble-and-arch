package com.ts.marblearch.api.administration.webAdapter;

import com.ts.marblearch.api.administration.webAdapter.commands.LoginCommand;
import com.ts.marblearch.api.administration.webAdapter.commands.RegisterUserCommand;
import com.ts.marblearch.api.administration.webAdapter.queries.GetAdminDashboardQuery;
import com.ts.marblearch.api.administration.webAdapter.requests.LoginRequest;
import com.ts.marblearch.api.administration.webAdapter.requests.RegisterRequest;
import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;
import com.ts.marblearch.api.sharedKernel.application.events.IModuleClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/${api.version}/auth")
@RequiredArgsConstructor
@Slf4j
public class AdministrationController {

    private final IModuleClient moduleClient;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<UUID>> register(@RequestBody @Valid RegisterRequest request) {
        CompletableFuture<UUID> resultFuture = new CompletableFuture<>();
        var command = new RegisterUserCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password(),
                request.role(),
                resultFuture
        );

        return moduleClient.executeCommandAsync(command)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public CompletableFuture<ResponseEntity<String>> login(@RequestBody @Valid LoginRequest request) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();
        var command = new LoginCommand(
                request.email(),
                request.password(),
                resultFuture
        );

        return moduleClient.executeCommandAsync(command)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<AdminDashboardDTO>> getDashboard() {
        CompletableFuture<AdminDashboardDTO> resultFuture = new CompletableFuture<>();
        var query = new GetAdminDashboardQuery(resultFuture);

        return moduleClient.executeQueryAsync(query)
                .thenApply(ResponseEntity::ok);
    }
}
