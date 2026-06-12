package com.ts.marblearch.api.crm.webAdapter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/${api.version}/broker/dashboard")
public class BrokerDashboardController {

    @GetMapping("/metrics")
    public CompletableFuture<ResponseEntity<DashboardMetricsResponse>> getMetrics() {
        // Return mock data to fulfill the API contract
        var response = new DashboardMetricsResponse(
                142,
                120,
                22,
                new BigDecimal("450000000"),
                Map.of(
                        "HOUSE", 40,
                        "PENTHOUSE", 25,
                        "APARTMENT", 60,
                        "CONDOMINIUM_HOUSE", 17
                ),
                15
        );
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    public record DashboardMetricsResponse(
            Integer totalProperties,
            Integer activeProperties,
            Integer inactiveProperties,
            BigDecimal totalMarketValue,
            Map<String, Integer> distributionByType,
            Integer recentLeads
    ) {}
}
