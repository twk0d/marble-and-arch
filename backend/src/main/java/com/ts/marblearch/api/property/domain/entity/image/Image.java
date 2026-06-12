package com.ts.marblearch.api.property.domain.entity.image;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Image {

    private final UUID id;
    private final UUID propertyId;
    private final String url;
    private String description;
    private boolean active;
    private boolean primary;
    private int displayOrder;

    public Image(UUID id, UUID propertyId, String url, String description, boolean active, boolean primary, int displayOrder) {
        this.id = id;
        this.propertyId = propertyId;
        this.url = url;
        this.description = description;
        this.active = active;
        this.primary = primary;
        this.displayOrder = displayOrder;
    }

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }

    public void setAsPrimary() {
        this.primary = true;
    }

    public void unsetPrimary() {
        this.primary = false;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}