package com.ts.marblearch.api.scheduling.application.handlers.command;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.scheduling.application.IVisitRepository;
import com.ts.marblearch.api.scheduling.domain.entity.Visit;
import com.ts.marblearch.api.scheduling.webAdapter.commands.ScheduleVisitCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScheduleVisitCommandHandler {

    private final IVisitRepository visitRepository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void handle(ScheduleVisitCommand command) {
        var request = command.getRequest();
        Visit visit = new Visit(
                UlidCreator.getMonotonicUlid().toUuid(),
                request.getPropertyId(),
                request.getClientId(),
                request.getBrokerId(),
                request.getScheduledDate(),
                command.getResultFuture()
        );

        visitRepository.save(visit);
        visit.publishEvents(eventPublisher);
    }
}
