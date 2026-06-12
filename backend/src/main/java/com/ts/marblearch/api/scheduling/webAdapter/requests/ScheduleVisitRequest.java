package com.ts.marblearch.api.scheduling.webAdapter.requests;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ScheduleVisitRequest {
    private UUID propertyId;
    private UUID clientId;
    private UUID brokerId;
    private LocalDateTime scheduledDate;
}
