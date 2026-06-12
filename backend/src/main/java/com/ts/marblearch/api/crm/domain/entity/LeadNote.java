package com.ts.marblearch.api.crm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LeadNote {
    private final UUID id;
    private final UUID leadId;
    private final UUID brokerId;
    private final String content;
    private final LocalDateTime createdAt;

    public LeadNote(UUID id, UUID leadId, UUID brokerId, String content) {
        this.id = id;
        this.leadId = leadId;
        this.brokerId = brokerId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
