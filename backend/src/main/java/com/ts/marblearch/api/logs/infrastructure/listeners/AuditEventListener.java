package com.ts.marblearch.api.logs.infrastructure.listeners;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.administration.domain.events.UserRegisteredEvent;
import com.ts.marblearch.api.logs.infrastructure.persistence.AuditLogJpaRepository;
import com.ts.marblearch.api.logs.infrastructure.persistence.model.AuditLogJpaEntity;
import com.ts.marblearch.api.property.domain.events.*;
import com.ts.marblearch.api.sharedKernel.infrastructure.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuditEventListener {

    private final AuditLogJpaRepository auditRepository;
    private final SecurityUtils securityUtils;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        saveLog("ADMINISTRATION", "USER_REGISTERED", event.getUser().getId(), event.getUser().getEmail().address(), "New user registered");
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePropertyCreated(PropertyCreatedEvent event) {
        saveLog("PROPERTY", "PROPERTY_CREATED", event.property().getId(), securityUtils.getCurrentUserEmail(), "New property listing created");
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePropertyDisabled(PropertyDisabledEvent event) {
        saveLog("PROPERTY", "PROPERTY_DISABLED", event.propertyId(), securityUtils.getCurrentUserEmail(), "Property listing deactivated");
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePropertyEnabled(PropertyEnabledEvent event) {
        saveLog("PROPERTY", "PROPERTY_ENABLED", event.propertyId(), securityUtils.getCurrentUserEmail(), "Property listing activated");
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleImageDisabled(ImageDisabledEvent event) {
        saveLog("PROPERTY", "IMAGE_DISABLED", event.imageId(), securityUtils.getCurrentUserEmail(), "Image disabled for property: " + event.propertyId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleImageEnabled(ImageEnabledEvent event) {
        saveLog("PROPERTY", "IMAGE_ENABLED", event.imageId(), securityUtils.getCurrentUserEmail(), "Image enabled for property: " + event.propertyId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePropertyUpdated(PropertyUpdatedEvent event) {
        saveLog("PROPERTY", "PROPERTY_UPDATED", event.property().getId(), securityUtils.getCurrentUserEmail(), "Property listing details updated");
    }

    private void saveLog(String module, String action, java.util.UUID resourceId, String performedBy, String details) {
        AuditLogJpaEntity log = new AuditLogJpaEntity(
                UlidCreator.getMonotonicUlid().toUuid(),
                module,
                action,
                resourceId,
                performedBy,
                details,
                LocalDateTime.now()
        );
        auditRepository.save(log);
    }
}
