package com.ts.marblearch.api.finance.infrastructure.persistence.model;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "commissions")
@Getter
@Setter
@NoArgsConstructor
public class CommissionJpaEntity {
    @Id
    private UUID id;
    private UUID brokerId;
    private UUID propertyId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime createdAt;

    public CommissionJpaEntity(UUID id, UUID brokerId, UUID propertyId, BigDecimal amount, Currency currency, LocalDateTime createdAt) {
        this.id = id;
        this.brokerId = brokerId;
        this.propertyId = propertyId;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}
