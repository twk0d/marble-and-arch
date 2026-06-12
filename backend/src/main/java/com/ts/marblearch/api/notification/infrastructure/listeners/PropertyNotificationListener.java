package com.ts.marblearch.api.notification.infrastructure.listeners;

import com.ts.marblearch.api.notification.application.IEmailSender;
import com.ts.marblearch.api.property.domain.events.PropertyCreatedEvent;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PropertyNotificationListener {

    private final IEmailSender emailSender;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePropertyCreated(PropertyCreatedEvent event) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("propertyId", event.property().getId().toString());
        variables.put("type", event.property().getType().name());

        // In a real scenario, we would send this to the property owner's email.
        // For now, we'll send it to a system administrator email placeholder.
        Email adminEmail = new Email("admin@marble-and-arch.com");

        emailSender.sendTemplateEmail(
                adminEmail,
                "Novo Imóvel Cadastrado: " + event.property().getId(),
                "property-created-email",
                variables
        );
    }
}
