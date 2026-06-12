package com.ts.marblearch.api.administration.webAdapter.queries;

import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;
import com.ts.marblearch.api.sharedKernel.application.events.query.BaseQuery;

import java.util.concurrent.CompletableFuture;

public class GetAdminDashboardQuery extends BaseQuery<AdminDashboardDTO> {
    public GetAdminDashboardQuery(CompletableFuture<AdminDashboardDTO> resultFuture) {
        super(GetAdminDashboardQuery.class, resultFuture);
    }
}
