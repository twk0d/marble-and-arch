package com.ts.marblearch.api.property.application.mappers.create;

import com.ts.marblearch.api.property.domain.entity.details.CountryHouseDetails;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreateCountryHouseRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateCountryHouseDetailsMapper implements ICreateDetailsMapper<CreateCountryHouseRequest, CountryHouseDetails> {
    @Override
    public CountryHouseDetails toDomain(CreateCountryHouseRequest request) {
        return new CountryHouseDetails(
                request.getBedrooms(),
                request.getSuites(),
                request.getBathrooms(),
                new Area(request.getTotalAreaValue(), request.getTotalAreaUnit()),
                new Area(request.getBuiltAreaValue(), request.getBuiltAreaUnit()),
                new Description(request.getDescription()),
                new PropertyFeatures(request.isHasGarage(), request.isHasPool(), false, false, request.isHasRiverOrLake(), false, false, false, false)
        );
    }

    @Override
    public PropertyType getSupportedType() {
        return PropertyType.COUNTRY_HOUSE;
    }
}
