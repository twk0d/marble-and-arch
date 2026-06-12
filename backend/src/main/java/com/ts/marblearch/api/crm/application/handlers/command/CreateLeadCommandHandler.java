package com.ts.marblearch.api.crm.application.handlers.command;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.crm.application.ILeadRepository;
import com.ts.marblearch.api.crm.domain.entity.Lead;
import com.ts.marblearch.api.crm.webAdapter.commands.CreateLeadCommand;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateLeadCommandHandler {

    private final ILeadRepository leadRepository;

    @EventListener
    @Transactional
    public void handle(CreateLeadCommand command) {
        var request = command.getRequest();
        Lead lead = new Lead(
                UlidCreator.getMonotonicUlid().toUuid(),
                request.getPropertyId(),
                request.getName(),
                new Email(request.getEmail()),
                new Phone(request.getPhone()),
                request.getBrokerId()
        );

        leadRepository.save(lead);
        command.getResultFuture().complete(lead.getId());
    }
}
