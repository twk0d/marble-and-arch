package com.ts.marblearch.api.scheduling.application;

import com.ts.marblearch.api.scheduling.domain.entity.Visit;

import java.util.Optional;
import java.util.UUID;

public interface IVisitRepository {
    Visit save(Visit visit);
    Optional<Visit> findById(UUID id);
}
