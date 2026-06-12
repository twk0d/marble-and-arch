package com.ts.marblearch.api.crm.infrastructure.persistence;

import com.ts.marblearch.api.crm.infrastructure.persistence.model.LeadJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeadJpaRepository extends JpaRepository<LeadJpaEntity, UUID> {
}
