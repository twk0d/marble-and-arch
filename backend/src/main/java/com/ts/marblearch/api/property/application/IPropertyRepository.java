package com.ts.marblearch.api.property.application;

import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import com.ts.marblearch.api.webAdapter.property.responses.PropertySummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for a repository that manages Property aggregates.
 * This interface is part of the application layer (the Port) and is technology-agnostic.
 * The infrastructure layer will provide the implementation.
 */
public interface IPropertyRepository {

    Optional<Property> findById(UUID propertyId);

    Property save(Property property);

    Page<Property> findAll(Pageable pageable);

    Page<Property> findAllWithFilters(PageableFilters filters, Pageable pageable);

    Page<PropertySummaryDTO> findAllSummariesWithFilters(PageableFilters filters, Pageable pageable);
}
