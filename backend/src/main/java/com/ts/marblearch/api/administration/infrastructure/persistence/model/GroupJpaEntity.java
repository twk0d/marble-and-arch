package com.ts.marblearch.api.administration.infrastructure.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class GroupJpaEntity {
    @Id
    private UUID id;
    private String name;
    private String description;

    public GroupJpaEntity(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
