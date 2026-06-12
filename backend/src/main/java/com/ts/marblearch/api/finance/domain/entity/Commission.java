package com.ts.marblearch.api.finance.domain.entity;

import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Commission extends AggregateRoot {
    private final UUID id;
    private final UUID brokerId;
    private final UUID propertyId;
    private final Money amount;
    private final LocalDateTime createdAt;

    public Commission(UUID id, UUID brokerId, UUID propertyId, Money amount) {
        this.id = id;
        this.brokerId = brokerId;
        this.propertyId = propertyId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
