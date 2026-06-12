package com.ts.marblearch.api.sharedKernel.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.AbstractAggregateRoot;

public abstract class AggregateRoot extends AbstractAggregateRoot<AggregateRoot> {
    public void publishEvents(ApplicationEventPublisher publisher) {
        this.domainEvents().forEach(publisher::publishEvent);
        this.clearDomainEvents();
    }
}
