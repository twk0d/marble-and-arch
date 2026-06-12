package com.ts.marblearch.api.property.infrastructure.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressJpaEntity {

    @Id
    private UUID id;

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String complement;
}
