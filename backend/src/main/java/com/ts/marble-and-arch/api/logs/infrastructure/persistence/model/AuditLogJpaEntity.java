package com.ts.marblearch.api.logs.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_logs_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String module;

    @Column(nullable = false)
    private String action;

    private UUID resourceId;

    private String performedBy;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
