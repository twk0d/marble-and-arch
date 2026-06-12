package com.ts.marblearch.api.crm.infrastructure.persistence;

import com.ts.marblearch.api.crm.application.ILeadRepository;
import com.ts.marblearch.api.crm.domain.entity.Lead;
import com.ts.marblearch.api.crm.domain.entity.LeadNote;
import com.ts.marblearch.api.crm.infrastructure.persistence.model.LeadJpaEntity;
import com.ts.marblearch.api.crm.infrastructure.persistence.model.LeadNoteJpaEntity;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LeadRepositoryImpl implements ILeadRepository {

    private final LeadJpaRepository jpaRepository;

    @Override
    public Lead save(Lead lead) {
        LeadJpaEntity jpaEntity = toJpaEntity(lead);
        LeadJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Lead> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private Lead toDomain(LeadJpaEntity jpaEntity) {
        List<LeadNote> notes = jpaEntity.getNotes().stream()
                .map(n -> new LeadNote(n.getId(), jpaEntity.getId(), n.getBrokerId(), n.getContent(), n.getCreatedAt()))
                .collect(Collectors.toList());

        return new Lead(
                jpaEntity.getId(),
                jpaEntity.getPropertyId(),
                jpaEntity.getName(),
                new Email(jpaEntity.getEmail()),
                new Phone(jpaEntity.getPhone()),
                jpaEntity.getStatus(),
                jpaEntity.getBrokerId(),
                notes
        );
    }

    private LeadJpaEntity toJpaEntity(Lead lead) {
        LeadJpaEntity jpaEntity = new LeadJpaEntity(
                lead.getId(),
                lead.getPropertyId(),
                lead.getName(),
                lead.getEmail().address(),
                lead.getPhone().number(),
                lead.getStatus(),
                lead.getBrokerId()
        );

        List<LeadNoteJpaEntity> notes = lead.getNotes().stream()
                .map(n -> new LeadNoteJpaEntity(n.getId(), jpaEntity, n.getBrokerId(), n.getContent(), n.getCreatedAt()))
                .collect(Collectors.toList());
        jpaEntity.setNotes(notes);

        return jpaEntity;
    }
}
