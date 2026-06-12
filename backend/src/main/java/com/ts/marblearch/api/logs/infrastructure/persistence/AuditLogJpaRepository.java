package com.ts.marblearch.api.logs.infrastructure.persistence;

import com.ts.marblearch.api.logs.infrastructure.persistence.model.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AuditLogJpaRepository extends JpaRepository<AuditLogJpaEntity, UUID> {

    @Query("SELECT COUNT(l) FROM AuditLogJpaEntity l WHERE l.timestamp > :since")
    long countRecentActivities(LocalDateTime since);
}
