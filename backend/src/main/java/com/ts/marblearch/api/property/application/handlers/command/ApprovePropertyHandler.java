package com.ts.marblearch.api.property.application.handlers.command;

import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.application.exceptions.PropertyNotFound;
import com.ts.marblearch.api.webAdapter.property.commands.ApprovePropertyCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ApprovePropertyHandler {
    private final IPropertyRepository repository;

    @EventListener
    @Transactional
    public void handle(ApprovePropertyCommand command) {
        var property = repository.findById(command.getAggregateId())
                .orElseThrow(() -> new PropertyNotFound("Property not found"));
        
        property.approve(command.getResultFuture());
        repository.save(property);
    }
}
