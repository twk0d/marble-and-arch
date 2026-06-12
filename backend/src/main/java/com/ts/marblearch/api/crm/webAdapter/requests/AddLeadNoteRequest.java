package com.ts.marblearch.api.crm.webAdapter.requests;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AddLeadNoteRequest {
    private UUID leadId;
    private UUID brokerId;
    private String content;
}
