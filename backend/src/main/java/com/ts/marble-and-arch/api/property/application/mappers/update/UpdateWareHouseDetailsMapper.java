package com.ts.marblearch.api.property.application.mappers.update;

import com.ts.marblearch.api.property.domain.entity.details.WareHouseDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.update.UpdateWareHouseRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdateWareHouseDetailsMapper implements IUpdateDetailsMapper<UpdateWareHouseRequest, WareHouseDetails> {
    @Override
    public WareHouseDetails toDomain(UpdateWareHouseRequest request) {
        return new WareHouseDetails(
                new Area(request.storageAreaValue(), request.storageAreaUnit()),
                new Area(request.officeAreaValue(), request.officeAreaUnit()),
                new Height(request.ceilingHeightValue()),
                request.loadingDocks(),
                new FloorType(request.floorType()),
                new PropertyFeatures(false, false, false, false, false, false, false, false, request.hasLoadingRamp())
        );
    }

    @Override
    public PropertyType getSupportedType() {
        return PropertyType.WAREHOUSE;
    }
}
