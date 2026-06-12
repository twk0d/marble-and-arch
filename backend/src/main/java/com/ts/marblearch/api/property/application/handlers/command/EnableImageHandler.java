package com.ts.marblearch.api.property.application.handlers.command;


import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.application.exceptions.PropertyNotFound;
import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.webAdapter.property.commands.EnableImageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EnableImageHandler {

    private final IPropertyRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void handle(EnableImageCommand command) {
        Property property = repository.findById(command.getPropertyUUID())
                .orElseThrow(() -> new PropertyNotFound("Cannot found a property with the UUID: " + command.getPropertyUUID()));

        property.enableImage(command.getImageUUID(), command.getResultFuture());

        property.publishEvents(eventPublisher);
    }
}