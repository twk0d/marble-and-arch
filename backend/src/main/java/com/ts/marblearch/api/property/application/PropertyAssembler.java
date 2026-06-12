package com.ts.marblearch.api.property.application;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.property.application.mappers.CreateDetailsMapperRegistry;
import com.ts.marblearch.api.property.application.mappers.UpdateDetailsMapperRegistry;
import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreatePropertyRequest;
import com.ts.marblearch.api.webAdapter.property.requests.update.UpdatePropertyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PropertyAssembler {

    private final CreateDetailsMapperRegistry createMapperRegistry;
    private final UpdateDetailsMapperRegistry updateMapperRegistry;

    @SuppressWarnings("unchecked")
    public Property assembleForCreation(CreatePropertyRequest request, CompletableFuture<UUID> future) {
        var mapper = createMapperRegistry.getMapper(request.getPropertyType());
        Object domainDetails = mapper.toDomain(request.getDetails());

        Address address = new Address(
                request.getStreet(),
                request.getNumber(),
                request.getNeighborhood(),
                request.getCity(),
                request.getState(),
                request.getPostalCode(),
                request.getComplement()
        );

        Money price = new Money(request.getPriceAmount(), request.getPriceCurrency());

        return new Property(
                UlidCreator.getMonotonicUlid().toUuid(),
                request.getTitle(),
                request.getDescription() != null ? new Description(request.getDescription()) : null,
                request.getPurpose(),
                PropertyStatus.PENDING,
                request.getBrokerId(),
                true,
                new ArrayList<>(),
                request.getPropertyType(),
                address,
                price,
                domainDetails,
                future
        );
    }

    @SuppressWarnings("unchecked")
    public void assembleForUpdate(Property existingProperty, UpdatePropertyRequest request, CompletableFuture<Void> future) {
        if (request.getPropertyType() != existingProperty.getType()) {
            throw new IllegalArgumentException("Changing property type during update is not allowed.");
        }
        var mapper = updateMapperRegistry.getMapper(request.getPropertyType());
        Object domainDetails = mapper.toDomain(request.getDetails());

        existingProperty.update(domainDetails, future);
    }
}
