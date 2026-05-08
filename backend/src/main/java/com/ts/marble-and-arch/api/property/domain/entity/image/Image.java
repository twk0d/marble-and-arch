package com.ts.marblearch.api.property.domain.entity.image;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Image {

    private final UUID id;
    private final UUID propertyId;
    private final String url;
    private String description;
    private boolean active;

    public Image(UUID id, UUID propertyId, String url, String description, boolean active) {
        this.id = id;
        this.propertyId = propertyId;
        this.url = url;
        this.description = description;
        this.active = active;
    }

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }
}