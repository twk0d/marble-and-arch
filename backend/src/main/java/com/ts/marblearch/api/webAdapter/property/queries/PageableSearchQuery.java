package com.ts.marblearch.api.webAdapter.property.queries;

import com.ts.marblearch.api.sharedKernel.application.events.query.BaseQuery;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import com.ts.marblearch.api.webAdapter.property.responses.PropertySummaryDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.concurrent.CompletableFuture;

@Getter
public class PageableSearchQuery extends BaseQuery<Page<PropertySummaryDTO>> {
    private final PageableFilters filters;
    private final int page;
    private final int size;

    public PageableSearchQuery(PageableFilters filters, int page, int size, CompletableFuture<Page<PropertySummaryDTO>> resultFuture) {
        super(filters, resultFuture);
        this.filters = filters;
        this.page = page;
        this.size = size;
    }
}