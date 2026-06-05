package com.ts.marblearch.api.webAdapter.property.requests;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Amenity;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class PageableFilters {
    private PropertyType type;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minBedrooms;
    private Integer minSuites;
    private Integer minParkingSpaces;
    private String city;
    private String state;
    private Set<Amenity> amenities;
}
