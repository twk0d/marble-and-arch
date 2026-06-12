package com.ts.marblearch.api.crm.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lead_notes")
@Getter
@Setter
@NoArgsConstructor
public class LeadNoteJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private LeadJpaEntity lead;

    @Column(name = "broker_id", nullable = false)
    private UUID brokerId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LeadNoteJpaEntity(UUID id, LeadJpaEntity lead, UUID brokerId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.lead = lead;
        this.brokerId = brokerId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
