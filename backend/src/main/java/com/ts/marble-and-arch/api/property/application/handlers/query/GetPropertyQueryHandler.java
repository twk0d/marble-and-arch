package com.ts.marblearch.api.property.application.handlers.query;

import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.webAdapter.property.queries.GetPropertyQuery;
import com.ts.marblearch.api.webAdapter.property.responses.PropertyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPropertyQueryHandler {

    private final IPropertyRepository propertyRepository;

    @EventListener
    public void handle(GetPropertyQuery query) {
        propertyRepository.findById(query.getPropertyId())
                .map(PropertyDTO::convert)
                .ifPresentOrElse(
                        query.getResultFuture()::complete,
                        () -> query.getResultFuture().completeExceptionally(new BussinessRuleValidationException("Property not found"))
                );
    }
}
