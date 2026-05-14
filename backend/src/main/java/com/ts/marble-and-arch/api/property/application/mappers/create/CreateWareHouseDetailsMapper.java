package com.ts.marblearch.api.property.application.mappers.create;

import com.ts.marblearch.api.property.domain.entity.details.WareHouseDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreateWareHouseRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateWareHouseDetailsMapper implements ICreateDetailsMapper<CreateWareHouseRequest, WareHouseDetails> {
    @Override
    public WareHouseDetails toDomain(CreateWareHouseRequest request) {
        return new WareHouseDetails(
                new Area(request.getStorageAreaValue(), request.getStorageAreaUnit()),
                new Area(request.getOfficeAreaValue(), request.getOfficeAreaUnit()),
                new Height(request.getCeilingHeightValue()),
                request.getLoadingDocks(),
                new FloorType(request.getFloorType()),
                new PropertyFeatures(false, false, false, false, false, false, false, false, request.isHasLoadingRamp())
        );
    }

    @Override
    public PropertyType getSupportedType() {
        return PropertyType.WAREHOUSE;
    }
}
