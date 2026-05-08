package com.ts.marblearch.api.property.domain.entity.property;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.property.domain.entity.image.Image;
import com.ts.marblearch.api.property.domain.events.*;
import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Address;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
@ToString
public class Property extends AggregateRoot {

    private UUID id;
    private boolean active;
    private List<Image> images;
    private PropertyType type;
    private Address address;
    private Money price;
    private Object details; // Generic object to hold specific details of property

    // Factory Constructor
    public Property(UUID id, boolean active, List<Image> images, PropertyType type, Address address, Money price, Object details, CompletableFuture<UUID> future) {
        validate(id, type, address, price, details);
        this.id = id;
        this.active = active;
        this.images = images != null ? images : new ArrayList<>();
        this.type = type;
        this.address = address;
        this.price = price;
        this.details = details;

        registerEvent(new PropertyCreatedEvent(this, future));
    }

    private void validate(UUID id, PropertyType type, Address address, Money price, Object details) {
        if (id == null) throw new BussinessRuleValidationException("Property ID cannot be null");
        if (type == null) throw new BussinessRuleValidationException("Property Type cannot be null");
        if (address == null) throw new BussinessRuleValidationException("Property address cannot be null");
        if (price == null) throw new BussinessRuleValidationException("Property price cannot be null");
        if (details == null) throw new BussinessRuleValidationException("Property details cannot be null");
    }

    // Metodo estático de fábrica para reconstrução a partir da persistência (usado pelo Repository)
    public static Property fromPersistence(UUID id, boolean active, List<Image> images, PropertyType type, Address address, Money price, Object details) {
        return new Property(id, active, images != null ? images : new ArrayList<>(), type, address, price, details);
    }

    public void disable(CompletableFuture<Void> future) {
        if (!this.active) {
            future.complete(null);
            return;
        }
        this.active = false;
        registerEvent(new PropertyDisabledEvent(this.id, future));
    }

    public void enable(CompletableFuture<Void> future) {
        if (this.active) {
            future.complete(null);
            return;
        }
        this.active = true;
        registerEvent(new PropertyEnabledEvent(this.id, future));
    }

    public void addImage(String url, String description) {
        if (url == null || url.isBlank()) throw new BussinessRuleValidationException("Image URL cannot be empty");
        Image newImage = new Image(UlidCreator.getMonotonicUlid().toUuid(), this.id, url, description, true);
        this.images.add(newImage);
    }

    public void enableImage(UUID imageId, CompletableFuture<Void> future) {
        findImageById(imageId).ifPresentOrElse(image -> {
            if (image.isActive()) {
                future.complete(null);
                return;
            }
            image.enable();
            registerEvent(new ImageEnabledEvent(this.id, imageId, future));
        }, () -> future.completeExceptionally(new BussinessRuleValidationException("Image not found")));
    }

    public void disableImage(UUID imageId, CompletableFuture<Void> future) {
        findImageById(imageId).ifPresentOrElse(image -> {
            if (!image.isActive()) {
                future.complete(null);
                return;
            }
            image.disable();
            registerEvent(new ImageDisabledEvent(this.id, imageId, future));
        }, () -> future.completeExceptionally(new BussinessRuleValidationException("Image not found")));
    }

    private Optional<Image> findImageById(UUID imageId) {
        return this.images.stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst();
    }

    public void update(Object details, CompletableFuture<Void> future) {
        if (details == null) throw new BussinessRuleValidationException("Update details cannot be null");
        this.details = details;
        registerEvent(new PropertyUpdatedEvent(this, future));
    }

    public void publishEvents(ApplicationEventPublisher publisher) {
        this.domainEvents().forEach(publisher::publishEvent);
    }
}
