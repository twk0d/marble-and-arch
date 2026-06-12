package com.ts.marblearch.api.property.infrastructure.persistence;


import com.ts.marblearch.api.administration.infrastructure.persistence.model.CityDistributionProjection;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.GeneralStatsProjection;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.TypeDistributionProjection;
import com.ts.marblearch.api.property.infrastructure.persistence.model.PropertyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyJpaRepository extends JpaRepository<PropertyJpaEntity, UUID>, JpaSpecificationExecutor<PropertyJpaEntity> {

    @Query("SELECT new com.ts.marblearch.api.administration.infrastructure.persistence.model.GeneralStatsProjection(" +
           "COUNT(p), " +
           "SUM(CASE WHEN p.active = true THEN 1L ELSE 0L END), " +
           "SUM(CASE WHEN p.active = true THEN p.priceAmount ELSE null END)) " +
           "FROM PropertyJpaEntity p")
    GeneralStatsProjection getGeneralStats();

    @Query("SELECT p.type as type, COUNT(p) as count FROM PropertyJpaEntity p GROUP BY p.type")
    List<TypeDistributionProjection> getTypeDistribution();

    @Query("SELECT p.address.city as city, COUNT(p) as count FROM PropertyJpaEntity p GROUP BY p.address.city")
    List<CityDistributionProjection> getCityDistribution();
}
