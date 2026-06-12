package com.ts.marblearch.api.finance.domain.entity;

import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PerformanceGoal extends AggregateRoot {
    private final UUID id;
    private final UUID brokerId;
    private int points;
    private String currentBadge;

    public PerformanceGoal(UUID id, UUID brokerId) {
        this.id = id;
        this.brokerId = brokerId;
        this.points = 0;
        this.currentBadge = "NOVICE";
    }

    public void addPoints(int points) {
        this.points += points;
        updateBadge();
    }

    private void updateBadge() {
        if (this.points > 1000) this.currentBadge = "ELITE";
        else if (this.points > 500) this.currentBadge = "PRO";
    }
}
