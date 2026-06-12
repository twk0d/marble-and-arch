package com.ts.marblearch.api.administration.application.handlers.query;

import com.ts.marblearch.api.administration.application.IDashboardRepository;
import com.ts.marblearch.api.administration.webAdapter.queries.GetAdminDashboardQuery;
import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAdminDashboardQueryHandler {

    private final IDashboardRepository dashboardRepository;

    @EventListener
    public void handle(GetAdminDashboardQuery query) {
        try {
            AdminDashboardDTO statistics = dashboardRepository.getDashboardStatistics();
            query.getResultFuture().complete(statistics);
        } catch (Exception e) {
            query.getResultFuture().completeExceptionally(e);
        }
    }
}
