package com.ts.marblearch.api.scheduling.infrastructure.persistence;

import com.ts.marblearch.api.scheduling.infrastructure.persistence.model.VisitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VisitJpaRepository extends JpaRepository<VisitJpaEntity, UUID> {
}
