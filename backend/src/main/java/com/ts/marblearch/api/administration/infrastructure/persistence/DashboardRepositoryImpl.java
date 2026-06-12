package com.ts.marblearch.api.administration.infrastructure.persistence;

import com.ts.marblearch.api.administration.application.IDashboardRepository;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.CityDistributionProjection;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.GeneralStatsProjection;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.TypeDistributionProjection;
import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;
import com.ts.marblearch.api.property.infrastructure.persistence.PropertyJpaRepository;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.logs.infrastructure.persistence.AuditLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements IDashboardRepository {

    private final PropertyJpaRepository propertyRepository;
    private final AuditLogJpaRepository auditLogRepository;

    @Override
    public AdminDashboardDTO getDashboardStatistics() {
        var generalStatsFuture = CompletableFuture.supplyAsync(propertyRepository::getGeneralStats);
        
        var typeDistFuture = CompletableFuture.supplyAsync(() -> 
                propertyRepository.getTypeDistribution().stream()
                        .collect(Collectors.toMap(TypeDistributionProjection::getType, TypeDistributionProjection::getCount)));

        var cityDistFuture = CompletableFuture.supplyAsync(() -> 
                propertyRepository.getCityDistribution().stream()
                        .collect(Collectors.toMap(CityDistributionProjection::getCity, CityDistributionProjection::getCount)));

        var recentActivitiesFuture = CompletableFuture.supplyAsync(() -> {
            LocalDateTime last24h = LocalDateTime.now().minusDays(1);
            return auditLogRepository.countRecentActivities(last24h);
        });

        // Wait for all to complete
        CompletableFuture.allOf(generalStatsFuture, typeDistFuture, cityDistFuture, recentActivitiesFuture).join();

        GeneralStatsProjection generalStats = generalStatsFuture.join();

        long total = generalStats.total() != null ? generalStats.total() : 0L;
        long active = generalStats.active() != null ? generalStats.active() : 0L;
        BigDecimal totalValue = generalStats.totalValue() != null ? generalStats.totalValue() : BigDecimal.ZERO;

        return new AdminDashboardDTO(
                total,
                active,
                total - active,
                totalValue,
                typeDistFuture.join(),
                cityDistFuture.join(),
                recentActivitiesFuture.join()
        );
    }
}
