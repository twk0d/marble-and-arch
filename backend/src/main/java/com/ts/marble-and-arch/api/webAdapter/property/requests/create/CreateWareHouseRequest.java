package com.ts.marblearch.api.webAdapter.property.requests.create;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.AreaUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateWareHouseRequest implements CreateDetailsRequest {
    private BigDecimal storageAreaValue;
    private AreaUnit storageAreaUnit;
    private BigDecimal officeAreaValue;
    private AreaUnit officeAreaUnit;
    private BigDecimal ceilingHeightValue;
    private int loadingDocks;
    private String floorType;
    private boolean hasLoadingRamp;
}
