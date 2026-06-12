package com.ts.marblearch.api.property.domain.entity.details;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Area;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.FloorType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Height;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyFeatures;

public record WareHouseDetails(Area storageArea, Area officeArea, Height ceilingHeight, int loadingDocks,
                               FloorType floorType, PropertyFeatures features) {
}
