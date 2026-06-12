package com.ts.marblearch.api.finance.webAdapter;

import com.ts.marblearch.api.finance.infrastructure.persistence.CommissionJpaRepository;
import com.ts.marblearch.api.finance.infrastructure.persistence.model.CommissionJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/${api.version}/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final CommissionJpaRepository commissionRepository;

    @GetMapping("/commissions/{brokerId}")
    public ResponseEntity<List<CommissionJpaEntity>> getBrokerCommissions(@PathVariable UUID brokerId) {
        return ResponseEntity.ok(commissionRepository.findByBrokerId(brokerId));
    }
}
