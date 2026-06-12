package com.ts.marblearch.api.property.application.mappers.create;

import com.ts.marblearch.api.property.domain.entity.details.PentHouseDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreatePentHouseRequest;
import org.springframework.stereotype.Component;

@Component
public class CreatePentHouseDetailsMapper implements ICreateDetailsMapper<CreatePentHouseRequest, PentHouseDetails> {
    @Override
    public PentHouseDetails toDomain(CreatePentHouseRequest request) {
        return new PentHouseDetails(
                request.getBedrooms(),
                request.getSuites(),
                request.getBathrooms(),
                request.getParkingSpaces(),
                new Area(request.getTotalAreaValue(), request.getTotalAreaUnit()),
                new Area(request.getBuiltAreaValue(), request.getBuiltAreaUnit()),
                new Area(request.getTerraceAreaValue(), request.getTerraceAreaUnit()),
                new Description(request.getViewDescription()),
                new PropertyFeatures(request.isHasGarage(), request.isHasPool(), false, request.isHasPrivateElevator(), false, false, false, false, false)
        );
    }

    @Override
    public PropertyType getSupportedType() {
        return PropertyType.PENTHOUSE;
    }
}
