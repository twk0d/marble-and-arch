package com.ts.marblearch.api.crm.infrastructure.persistence.model;

import com.ts.marblearch.api.crm.domain.entity.LeadStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "leads")
@Getter
@Setter
@NoArgsConstructor
public class LeadJpaEntity {

    @Id
    private UUID id;

    @Column(name = "property_id", nullable = false)
    private UUID propertyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    @Column(name = "broker_id")
    private UUID brokerId;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeadNoteJpaEntity> notes = new ArrayList<>();

    public LeadJpaEntity(UUID id, UUID propertyId, String name, String email, String phone, LeadStatus status, UUID brokerId) {
        this.id = id;
        this.propertyId = propertyId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.brokerId = brokerId;
    }
}
