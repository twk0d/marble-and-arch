package com.ts.marblearch.api.property.domain.entity.property;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.property.domain.entity.image.Image;
import com.ts.marblearch.api.property.domain.events.*;
import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
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
    private String title;
    private Description description;
    private PropertyPurpose purpose;
    private PropertyStatus status;
    private UUID brokerId;
    private boolean active;
    private List<Image> images;
    private PropertyType type;
    private Address address;
    private Money price;
    private Object details; // Generic object to hold specific details of property

    // Factory Constructor
    public Property(UUID id, String title, Description description, PropertyPurpose purpose, PropertyStatus status, UUID brokerId, boolean active, List<Image> images, PropertyType type, Address address, Money price, Object details, CompletableFuture<UUID> future) {
        validate(id, title, type, address, price, details);
        this.id = id;
        this.title = title;
        this.description = description;
        this.purpose = purpose;
        this.status = status;
        this.brokerId = brokerId;
        this.active = active;
        this.images = images != null ? images : new ArrayList<>();
        this.type = type;
        this.address = address;
        this.price = price;
        this.details = details;

        if (future != null) {
            registerEvent(new PropertyCreatedEvent(this, future));
        }
    }

    private void validate(UUID id, String title, PropertyType type, Address address, Money price, Object details) {
        if (id == null) throw new BussinessRuleValidationException("Property ID cannot be null");
        if (title == null || title.isBlank()) throw new BussinessRuleValidationException("Property title cannot be empty");
        if (type == null) throw new BussinessRuleValidationException("Property Type cannot be null");
        if (address == null) throw new BussinessRuleValidationException("Property address cannot be null");
        if (price == null) throw new BussinessRuleValidationException("Property price cannot be null");
        if (details == null) throw new BussinessRuleValidationException("Property details cannot be null");
    }

    // Metodo estático de fábrica para reconstrução a partir da persistência (usado pelo Repository)
    public static Property fromPersistence(UUID id, String title, Description description, PropertyPurpose purpose, PropertyStatus status, UUID brokerId, boolean active, List<Image> images, PropertyType type, Address address, Money price, Object details) {
        return new Property(id, title, description, purpose, status, brokerId, active, images != null ? images : new ArrayList<>(), type, address, price, details, null);
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

    public void addImage(String url, String description, boolean primary) {
        if (url == null || url.isBlank()) throw new BussinessRuleValidationException("Image URL cannot be empty");
        int nextOrder = this.images.size();
        Image newImage = new Image(UlidCreator.getMonotonicUlid().toUuid(), this.id, url, description, true, primary, nextOrder);
        
        if (primary) {
            this.images.forEach(Image::unsetPrimary);
        }
        
        this.images.add(newImage);
    }

    public void approve(CompletableFuture<Void> future) {
        this.status = PropertyStatus.APPROVED;
        // In a real scenario, we might want to register a PropertyApprovedEvent
        future.complete(null);
    }

    public void reject(CompletableFuture<Void> future) {
        this.status = PropertyStatus.REJECTED;
        // In a real scenario, we might want to register a PropertyRejectedEvent
        future.complete(null);
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
