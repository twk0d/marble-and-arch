package com.ts.marblearch.api.property.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class ImageJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyJpaEntity property;

    private String url;
    private String description;
    private boolean active;

    public ImageJpaEntity(UUID id, PropertyJpaEntity property, String url, String description, boolean active) {
        this.id = id;
        this.property = property;
        this.url = url;
        this.description = description;
        this.active = active;
    }
}
