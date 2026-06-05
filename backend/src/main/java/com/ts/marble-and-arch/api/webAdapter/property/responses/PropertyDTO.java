package com.ts.marblearch.api.webAdapter.property.responses;

import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Address;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private UUID id;
    private boolean active;
    private List<ImageDTO> images;
    private PropertyType type;
    private Address address;
    private Money price;
    private Object details;

    public static PropertyDTO convert(Property property) {
        List<ImageDTO> imageDTOS = property.getImages().stream()
                .map(ImageDTO::createDTO)
                .collect(Collectors.toList());
        return new PropertyDTO(
                property.getId(),
                property.isActive(),
                imageDTOS,
                property.getType(),
                property.getAddress(),
                property.getPrice(),
                property.getDetails()
        );
    }
}
