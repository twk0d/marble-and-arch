package com.ts.marblearch.api.scheduling.domain.events;

import com.ts.marblearch.api.scheduling.domain.entity.Visit;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public record VisitScheduledEvent(Visit visit, CompletableFuture<UUID> future) {
}
