package com.ts.marblearch.api.finance.infrastructure.persistence;

import com.ts.marblearch.api.finance.infrastructure.persistence.model.CommissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommissionJpaRepository extends JpaRepository<CommissionJpaEntity, UUID> {
    List<CommissionJpaEntity> findByBrokerId(UUID brokerId);
}
