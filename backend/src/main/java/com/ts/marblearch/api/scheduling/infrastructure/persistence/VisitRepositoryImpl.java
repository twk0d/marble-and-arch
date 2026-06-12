package com.ts.marblearch.api.scheduling.infrastructure.persistence;

import com.ts.marblearch.api.scheduling.application.IVisitRepository;
import com.ts.marblearch.api.scheduling.domain.entity.Visit;
import com.ts.marblearch.api.scheduling.infrastructure.persistence.model.VisitJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VisitRepositoryImpl implements IVisitRepository {

    private final VisitJpaRepository jpaRepository;

    @Override
    public Visit save(Visit visit) {
        VisitJpaEntity jpaEntity = new VisitJpaEntity(
                visit.getId(),
                visit.getPropertyId(),
                visit.getClientId(),
                visit.getBrokerId(),
                visit.getScheduledDate(),
                visit.getStatus()
        );
        jpaRepository.save(jpaEntity);
        return visit;
    }

    @Override
    public Optional<Visit> findById(UUID id) {
        return jpaRepository.findById(id).map(jpa -> new Visit(
                jpa.getId(),
                jpa.getPropertyId(),
                jpa.getClientId(),
                jpa.getBrokerId(),
                jpa.getScheduledDate(),
                jpa.getStatus()
        ));
    }
}
