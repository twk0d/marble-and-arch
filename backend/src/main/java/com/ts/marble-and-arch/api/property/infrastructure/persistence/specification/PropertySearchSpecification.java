package com.ts.marblearch.api.property.infrastructure.persistence.specification;

import com.ts.marblearch.api.property.infrastructure.persistence.model.PropertyJpaEntity;
import com.ts.marblearch.api.property.infrastructure.persistence.model.details.CondominiumHouseDetails;
import com.ts.marblearch.api.property.infrastructure.persistence.model.details.CondominiumPlotDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Amenity;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySearchSpecification {

    public static Specification<PropertyJpaEntity> withFilters(PageableFilters filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Core Filters (Base Entity)
            predicates.add(cb.equal(root.get("active"), true));

            if (filters.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filters.getType()));
            }

            if (filters.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("priceAmount"), filters.getMinPrice()));
            }

            if (filters.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("priceAmount"), filters.getMaxPrice()));
            }

            if (filters.getCity() != null && !filters.getCity().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("city")), "%" + filters.getCity().toLowerCase() + "%"));
            }

            if (filters.getState() != null && !filters.getState().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("state")), filters.getState().toLowerCase()));
            }

            // 2. Detail-Specific Filters (requires Joins)
            if (filters.getMinBedrooms() != null) {
                Predicate houseBedrooms = cb.greaterThanOrEqualTo(root.join("houseDetails", JoinType.LEFT).get("bedrooms"), filters.getMinBedrooms());
                Predicate condoBedrooms = cb.greaterThanOrEqualTo(root.join("condominiumHouseDetails", JoinType.LEFT).get("bedrooms"), filters.getMinBedrooms());
                Predicate countryBedrooms = cb.greaterThanOrEqualTo(root.join("countryHouseDetails", JoinType.LEFT).get("bedrooms"), filters.getMinBedrooms());
                Predicate pentHouseBedrooms = cb.greaterThanOrEqualTo(root.join("pentHouseDetails", JoinType.LEFT).get("bedrooms"), filters.getMinBedrooms());
                predicates.add(cb.or(houseBedrooms, condoBedrooms, countryBedrooms, pentHouseBedrooms));
            }

            if (filters.getMinSuites() != null) {
                Predicate houseSuites = cb.greaterThanOrEqualTo(root.join("houseDetails", JoinType.LEFT).get("suites"), filters.getMinSuites());
                Predicate condoSuites = cb.greaterThanOrEqualTo(root.join("condominiumHouseDetails", JoinType.LEFT).get("suites"), filters.getMinSuites());
                Predicate countrySuites = cb.greaterThanOrEqualTo(root.join("countryHouseDetails", JoinType.LEFT).get("suites"), filters.getMinSuites());
                Predicate pentHouseSuites = cb.greaterThanOrEqualTo(root.join("pentHouseDetails", JoinType.LEFT).get("suites"), filters.getMinSuites());
                predicates.add(cb.or(houseSuites, condoSuites, countrySuites, pentHouseSuites));
            }

            if (filters.getMinParkingSpaces() != null) {
                Predicate houseParking = cb.greaterThanOrEqualTo(root.join("houseDetails", JoinType.LEFT).get("parkingSpaces"), filters.getMinParkingSpaces());
                Predicate condoParking = cb.greaterThanOrEqualTo(root.join("condominiumHouseDetails", JoinType.LEFT).get("parkingSpaces"), filters.getMinParkingSpaces());
                Predicate officeParking = cb.greaterThanOrEqualTo(root.join("officeDetails", JoinType.LEFT).get("parkingSpaces"), filters.getMinParkingSpaces());
                Predicate pentHouseParking = cb.greaterThanOrEqualTo(root.join("pentHouseDetails", JoinType.LEFT).get("parkingSpaces"), filters.getMinParkingSpaces());
                predicates.add(cb.or(houseParking, condoParking, officeParking, pentHouseParking));
            }

            // 3. Amenity Filters
            if (filters.getAmenities() != null && !filters.getAmenities().isEmpty()) {
                for (Amenity amenity : filters.getAmenities()) {
                    List<Predicate> amenityPredicates = new ArrayList<>();

                    // Check in CondominiumHouseDetails
                    amenityPredicates.add(cb.isMember(amenity, root.join("condominiumHouseDetails", JoinType.LEFT).get("amenities")));

                    // Check in CondominiumPlotDetails
                    amenityPredicates.add(cb.isMember(amenity, root.join("condominiumPlotDetails", JoinType.LEFT).get("amenities")));

                    // Special cases for boolean flags that map to Amenities
                    if (amenity == Amenity.POOL) {
                        amenityPredicates.add(cb.isTrue(root.join("houseDetails", JoinType.LEFT).get("hasPool")));
                        amenityPredicates.add(cb.isTrue(root.join("pentHouseDetails", JoinType.LEFT).get("hasPool")));
                        amenityPredicates.add(cb.isTrue(root.join("countryHouseDetails", JoinType.LEFT).get("hasPool")));
                    }

                    predicates.add(cb.or(amenityPredicates.toArray(new Predicate[0])));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
