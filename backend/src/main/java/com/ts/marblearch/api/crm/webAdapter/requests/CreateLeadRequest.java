package com.ts.marblearch.api.crm.webAdapter.requests;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateLeadRequest {
    private UUID propertyId;
    private String name;
    private String email;
    private String phone;
    private UUID brokerId;
}
