package com.ts.marblearch.api.scheduling.infrastructure.persistence.model;

import com.ts.marblearch.api.scheduling.domain.entity.VisitStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
public class VisitJpaEntity {

    @Id
    private UUID id;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "broker_id", nullable = false)
    private UUID brokerId;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    public VisitJpaEntity(UUID id, UUID propertyId, UUID clientId, UUID brokerId, LocalDateTime scheduledDate, VisitStatus status) {
        this.id = id;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.brokerId = brokerId;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }
}
