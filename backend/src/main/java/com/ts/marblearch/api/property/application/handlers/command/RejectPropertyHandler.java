package com.ts.marblearch.api.property.application.handlers.command;

import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.application.exceptions.PropertyNotFound;
import com.ts.marblearch.api.webAdapter.property.commands.RejectPropertyCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RejectPropertyHandler {
    private final IPropertyRepository repository;

    @EventListener
    @Transactional
    public void handle(RejectPropertyCommand command) {
        var property = repository.findById(command.getAggregateId())
                .orElseThrow(() -> new PropertyNotFound("Property not found"));
        
        property.reject(command.getResultFuture());
        repository.save(property);
    }
}
