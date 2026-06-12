package com.ts.marblearch.api.administration.webAdapter.commands;

import com.ts.marblearch.api.sharedKernel.application.events.commands.BaseCommand;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
public class LoginCommand extends BaseCommand<String> {
    private final String email;
    private final String password;

    public LoginCommand(String email, String password, CompletableFuture<String> resultFuture) {
        super(email, resultFuture);
        this.email = email;
        this.password = password;
    }
}
