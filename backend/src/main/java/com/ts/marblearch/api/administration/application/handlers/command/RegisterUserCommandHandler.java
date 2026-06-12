package com.ts.marblearch.api.administration.application.handlers.command;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.administration.application.IPasswordHasher;
import com.ts.marblearch.api.administration.application.IUserRepository;
import com.ts.marblearch.api.administration.domain.entity.user.Role;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.webAdapter.commands.RegisterUserCommand;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ts.marblearch.api.administration.domain.events.UserRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandHandler {

    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void handle(RegisterUserCommand command) {
        Email email = new Email(command.getEmail());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new BussinessRuleValidationException("User already exists with this email");
        }

        User user = new User(
                UlidCreator.getMonotonicUlid().toUuid(),
                new Name(command.getFirstName(), command.getLastName()),
                email,
                passwordHasher.hash(command.getPassword()),
                Role.valueOf(command.getRole().toUpperCase())
        );

        userRepository.save(user);
        
        eventPublisher.publishEvent(new UserRegisteredEvent(user));
        
        command.getResultFuture().complete(user.getId());
    }
}
