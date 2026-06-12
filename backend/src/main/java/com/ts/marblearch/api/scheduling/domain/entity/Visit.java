package com.ts.marblearch.api.scheduling.domain.entity;

import com.ts.marblearch.api.scheduling.domain.events.VisitScheduledEvent;
import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
@ToString
public class Visit extends AggregateRoot {
    private final UUID id;
    private final UUID propertyId;
    private final UUID clientId;
    private final UUID brokerId;
    private final LocalDateTime scheduledDate;
    private VisitStatus status;

    public Visit(UUID id, UUID propertyId, UUID clientId, UUID brokerId, LocalDateTime scheduledDate, CompletableFuture<UUID> future) {
        if (id == null) throw new BussinessRuleValidationException("Visit ID cannot be null");
        if (propertyId == null) throw new BussinessRuleValidationException("Property ID cannot be null");
        if (clientId == null) throw new BussinessRuleValidationException("Client ID cannot be null");
        if (brokerId == null) throw new BussinessRuleValidationException("Broker ID cannot be null");
        if (scheduledDate == null) throw new BussinessRuleValidationException("Scheduled date cannot be null");

        this.id = id;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.brokerId = brokerId;
        this.scheduledDate = scheduledDate;
        this.status = VisitStatus.SCHEDULED;

        registerEvent(new VisitScheduledEvent(this, future));
    }

    public void updateStatus(VisitStatus newStatus) {
        this.status = newStatus;
    }
}
