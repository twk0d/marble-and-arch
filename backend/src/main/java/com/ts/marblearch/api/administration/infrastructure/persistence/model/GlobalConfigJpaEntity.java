package com.ts.marblearch.api.administration.infrastructure.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "global_configs")
@Getter
@Setter
@NoArgsConstructor
public class GlobalConfigJpaEntity {
    @Id
    private String configKey;
    private String configValue;
    private String description;

    public GlobalConfigJpaEntity(String configKey, String configValue, String description) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
    }
}
