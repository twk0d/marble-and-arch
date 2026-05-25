package com.ts.marblearch.api.administration.application;

import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;

public interface IDashboardRepository {
    AdminDashboardDTO getDashboardStatistics();
}
