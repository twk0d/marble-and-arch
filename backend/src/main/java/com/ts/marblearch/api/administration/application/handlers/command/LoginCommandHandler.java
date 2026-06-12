package com.ts.marblearch.api.administration.application.handlers.command;

import com.ts.marblearch.api.administration.application.IPasswordHasher;
import com.ts.marblearch.api.administration.application.IUserRepository;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.infrastructure.services.JwtService;
import com.ts.marblearch.api.administration.webAdapter.commands.LoginCommand;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler {

    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;
    private final JwtService jwtService;

    @EventListener
    public void handle(LoginCommand command) {
        User user = userRepository.findByEmail(new Email(command.getEmail()))
                .orElseThrow(() -> new BussinessRuleValidationException("Invalid email or password"));

        if (!passwordHasher.matches(command.getPassword(), user.getPassword())) {
            throw new BussinessRuleValidationException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new BussinessRuleValidationException("User account is inactive");
        }

        String token = jwtService.generateToken(user);
        command.getResultFuture().complete(token);
    }
}
