package com.ts.marblearch.api.notification.infrastructure.listeners;

import com.ts.marblearch.api.administration.domain.events.UserRegisteredEvent;
import com.ts.marblearch.api.notification.application.IEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserNotificationListener {

    private final IEmailSender emailSender;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", event.getUser().getName().firstName());
        variables.put("role", event.getUser().getRole().name());

        emailSender.sendTemplateEmail(
                event.getUser().getEmail(),
                "Bem-vindo à Marble & Arch!",
                "welcome-email",
                variables
        );
    }
}
