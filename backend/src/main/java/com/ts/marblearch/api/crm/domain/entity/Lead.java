package com.ts.marblearch.api.crm.domain.entity;

import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class Lead extends AggregateRoot {
    private final UUID id;
    private final UUID propertyId;
    private final String name;
    private final Email email;
    private final Phone phone;
    private LeadStatus status;
    private UUID brokerId;
    private final List<LeadNote> notes;

    public Lead(UUID id, UUID propertyId, String name, Email email, Phone phone, UUID brokerId) {
        if (id == null) throw new BussinessRuleValidationException("Lead ID cannot be null");
        if (propertyId == null) throw new BussinessRuleValidationException("Property ID cannot be null");
        if (name == null || name.isBlank()) throw new BussinessRuleValidationException("Lead name cannot be empty");
        if (email == null) throw new BussinessRuleValidationException("Lead email cannot be null");

        this.id = id;
        this.propertyId = propertyId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = LeadStatus.NEW;
        this.brokerId = brokerId;
        this.notes = new ArrayList<>();
    }

    public void updateStatus(LeadStatus newStatus) {
        if (newStatus == null) throw new BussinessRuleValidationException("Status cannot be null");
        this.status = newStatus;
    }

    public void assignBroker(UUID brokerId) {
        this.brokerId = brokerId;
    }

    public void addNote(UUID noteId, UUID brokerId, String content) {
        this.notes.add(new LeadNote(noteId, this.id, brokerId, content));
    }
}
