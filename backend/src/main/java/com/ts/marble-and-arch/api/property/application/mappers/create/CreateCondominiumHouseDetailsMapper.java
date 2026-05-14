package com.ts.marblearch.api.property.application.mappers.create;

import com.ts.marblearch.api.property.domain.entity.details.CondominiumHouseDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreateCondominiumHouseRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateCondominiumHouseDetailsMapper implements ICreateDetailsMapper<CreateCondominiumHouseRequest, CondominiumHouseDetails> {
    @Override
    public CondominiumHouseDetails toDomain(CreateCondominiumHouseRequest request) {
        return new CondominiumHouseDetails(
                request.getBedrooms(),
                request.getSuites(),
                request.getBathrooms(),
                request.getParkingSpaces(),
                request.getFloor(),
                new Area(request.getTotalAreaValue(), request.getTotalAreaUnit()),
                new Area(request.getBuiltAreaValue(), request.getBuiltAreaUnit()),
                new Name(request.getCondominiumName(), null),
                new Money(request.getCondominiumFeeAmount(), request.getCondominiumFeeCurrency()),
                request.getAmenities(),
                new PropertyFeatures(request.isHasGarage(), false, request.isHasBalcony(), false, false, false, false, false, false)
        );
    }

    @Override
    public PropertyType getSupportedType() {
        return PropertyType.CONDOMINIUM_HOUSE;
    }
}
