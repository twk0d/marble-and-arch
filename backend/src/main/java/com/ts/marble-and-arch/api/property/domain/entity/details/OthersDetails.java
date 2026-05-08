package com.ts.marblearch.api.property.domain.entity.details;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Area;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Description;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.LegalStatus;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.YearBuilt;

public record OthersDetails(Description typeDescription, Area area, YearBuilt yearBuilt, LegalStatus legalStatus,
                            Description description) {
}
