package com.ts.marblearch.api.crm.application;

import com.ts.marblearch.api.crm.domain.entity.Lead;

import java.util.Optional;
import java.util.UUID;

public interface ILeadRepository {
    Lead save(Lead lead);
    Optional<Lead> findById(UUID id);
}
