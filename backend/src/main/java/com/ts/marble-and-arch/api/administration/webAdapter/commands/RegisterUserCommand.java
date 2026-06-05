package com.ts.marblearch.api.administration.webAdapter.commands;

import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class RegisterUserCommand extends BaseCommand<UUID> {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String role;

    public RegisterUserCommand(String firstName, String lastName, String email, String password, String role, CompletableFuture<UUID> resultFuture) {
        super(email, resultFuture);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
