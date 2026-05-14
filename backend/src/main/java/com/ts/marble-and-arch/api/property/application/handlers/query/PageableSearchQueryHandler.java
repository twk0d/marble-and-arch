package com.ts.marblearch.api.property.application.handlers.query;

import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.webAdapter.property.queries.PageableSearchQuery;
import com.ts.marblearch.api.webAdapter.property.responses.PropertySummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageableSearchQueryHandler {

    private final IPropertyRepository propertyRepository;

    @EventListener
    public void handle(PageableSearchQuery query) {
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
            Page<PropertySummaryDTO> result = propertyRepository
                    .findAllSummariesWithFilters(query.getFilters(), pageable);
            query.getResultFuture().complete(result);
        } catch (Exception e) {
            query.getResultFuture().completeExceptionally(e);
        }
    }
}