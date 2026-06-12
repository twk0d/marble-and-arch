package com.ts.marblearch.api.property.infrastructure.persistence;

import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.domain.entity.image.Image;
import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.property.infrastructure.persistence.mapper.DetailsMapperRegistry;
import com.ts.marblearch.api.property.infrastructure.persistence.model.AddressJpaEntity;
import com.ts.marblearch.api.property.infrastructure.persistence.model.ImageJpaEntity;
import com.ts.marblearch.api.property.infrastructure.persistence.model.PropertyJpaEntity;
import com.ts.marblearch.api.property.infrastructure.persistence.specification.PropertySearchSpecification;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Address;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import com.ts.marblearch.api.webAdapter.property.responses.PropertySummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements IPropertyRepository {

    private final PropertyJpaRepository jpaRepository;
    private final DetailsMapperRegistry mapperRegistry;

    @Override
    public Optional<Property> findById(UUID propertyId) {
        return jpaRepository.findById(propertyId).map(this::toDomain);
    }

    @Override
    public Property save(Property property) {
        PropertyJpaEntity jpaEntity = toJpaEntity(property);
        PropertyJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Page<Property> findAll(Pageable pageable) {
        Page<PropertyJpaEntity> jpaEntities = jpaRepository.findAll(pageable);
        List<Property> domainProperties = jpaEntities.getContent().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(domainProperties, pageable, jpaEntities.getTotalElements());
    }

    @Override
    public Page<Property> findAllWithFilters(PageableFilters filters, Pageable pageable) {
        Page<PropertyJpaEntity> jpaEntities = jpaRepository.findAll(PropertySearchSpecification.withFilters(filters), pageable);
        List<Property> domainProperties = jpaEntities.getContent().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(domainProperties, pageable, jpaEntities.getTotalElements());
    }

    @Override
    public Page<PropertySummaryDTO> findAllSummariesWithFilters(PageableFilters filters, Pageable pageable) {
        Page<PropertyJpaEntity> jpaEntities = jpaRepository.findAll(PropertySearchSpecification.withFilters(filters), pageable);
        return jpaEntities.map(jpa -> {
            String firstImageUrl = null;
            if (jpa.getImages() != null && !jpa.getImages().isEmpty()) {
                firstImageUrl = jpa.getImages().stream()
                        .filter(ImageJpaEntity::isActive)
                        .findFirst()
                        .map(ImageJpaEntity::getUrl)
                        .orElse(null);
            }

            return new PropertySummaryDTO(
                jpa.getId(),
                jpa.getType(),
                jpa.getAddress().getCity(),
                jpa.getAddress().getState(),
                jpa.getPriceAmount(),
                jpa.getPriceCurrency().name(),
                firstImageUrl,
                jpa.isActive()
            );
        });
    }

    @SuppressWarnings("unchecked")
    private Property toDomain(PropertyJpaEntity jpaEntity) {
        List<Image> images = jpaEntity.getImages().stream()
                .map(img -> new Image(img.getId(), jpaEntity.getId(), img.getUrl(), img.getDescription(), img.isActive(), img.isPrimary(), img.getDisplayOrder()))
                .collect(Collectors.toList());

        var mapper = mapperRegistry.getMapper(jpaEntity.getType());
        Object details = mapper.toDomain(getJpaDetails(jpaEntity));

        AddressJpaEntity addressJpa = jpaEntity.getAddress();
        Address address = new Address(
                addressJpa.getStreet(),
                addressJpa.getNumber(),
                addressJpa.getNeighborhood(),
                addressJpa.getCity(),
                addressJpa.getState(),
                addressJpa.getPostalCode(),
                addressJpa.getComplement()
        );

        Money price = new Money(jpaEntity.getPriceAmount(), jpaEntity.getPriceCurrency());

        return Property.fromPersistence(
                jpaEntity.getId(),
                jpaEntity.getTitle(),
                jpaEntity.getDescription() != null ? new com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Description(jpaEntity.getDescription()) : null,
                jpaEntity.getPurpose(),
                jpaEntity.getStatus(),
                jpaEntity.getBrokerId(),
                jpaEntity.isActive(),
                images,
                jpaEntity.getType(),
                address,
                price,
                details
        );
    }

    private Object getJpaDetails(PropertyJpaEntity jpaEntity) {
        return switch (jpaEntity.getType()) {
            case HOUSE -> jpaEntity.getHouseDetails();
            case CONDOMINIUM_HOUSE -> jpaEntity.getCondominiumHouseDetails();
            case CONDOMINIUM_PLOT -> jpaEntity.getCondominiumPlotDetails();
            case COUNTRY_HOUSE -> jpaEntity.getCountryHouseDetails();
            case OFFICE -> jpaEntity.getOfficeDetails();
            case OTHERS -> jpaEntity.getOthersDetails();
            case PENTHOUSE -> jpaEntity.getPentHouseDetails();
            case PLOT -> jpaEntity.getPlotDetails();
            case STUDIO -> jpaEntity.getStudioDetails();
            case WAREHOUSE -> jpaEntity.getWareHouseDetails();
        };
    }

    @SuppressWarnings("unchecked")
    private PropertyJpaEntity toJpaEntity(Property domainProperty) {
        PropertyJpaEntity jpaEntity = new PropertyJpaEntity(
                domainProperty.getId(),
                domainProperty.isActive(),
                domainProperty.getType()
        );

        jpaEntity.setTitle(domainProperty.getTitle());
        jpaEntity.setDescription(domainProperty.getDescription() != null ? domainProperty.getDescription().text() : null);
        jpaEntity.setPurpose(domainProperty.getPurpose());
        jpaEntity.setStatus(domainProperty.getStatus());
        jpaEntity.setBrokerId(domainProperty.getBrokerId());

        Address addressDomain = domainProperty.getAddress();
        AddressJpaEntity addressJpa = new AddressJpaEntity(
                com.github.f4b6a3.ulid.UlidCreator.getMonotonicUlid().toUuid(),
                addressDomain.street(),
                addressDomain.number(),
                addressDomain.neighborhood(),
                addressDomain.city(),
                addressDomain.state(),
                addressDomain.postalCode(),
                addressDomain.complement()
        );
        jpaEntity.setAddress(addressJpa);

        jpaEntity.setPriceAmount(domainProperty.getPrice().amount());
        jpaEntity.setPriceCurrency(domainProperty.getPrice().currency());

        List<ImageJpaEntity> imageJpaEntities = domainProperty.getImages().stream()
                .map(img -> new ImageJpaEntity(img.getId(), jpaEntity, img.getUrl(), img.getDescription(), img.isActive(), img.isPrimary(), img.getDisplayOrder()))
                .collect(Collectors.toList());
        jpaEntity.setImages(imageJpaEntities);

        if (domainProperty.getDetails() != null) {
            var mapper = mapperRegistry.getMapper(domainProperty.getType());
            Object jpaDetails = mapper.toJpaEntity(domainProperty.getDetails());
            setJpaDetails(jpaEntity, jpaDetails);
        }

        return jpaEntity;
    }

    private void setJpaDetails(PropertyJpaEntity jpaEntity, Object jpaDetails) {
        switch (jpaEntity.getType()) {
            case HOUSE ->
                    jpaEntity.setHouseDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.HouseDetails) jpaDetails);
            case CONDOMINIUM_HOUSE ->
                    jpaEntity.setCondominiumHouseDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.CondominiumHouseDetails) jpaDetails);
            case CONDOMINIUM_PLOT ->
                    jpaEntity.setCondominiumPlotDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.CondominiumPlotDetails) jpaDetails);
            case COUNTRY_HOUSE ->
                    jpaEntity.setCountryHouseDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.CountryHouseDetails) jpaDetails);
            case OFFICE ->
                    jpaEntity.setOfficeDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.OfficeDetails) jpaDetails);
            case OTHERS ->
                    jpaEntity.setOthersDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.OthersDetails) jpaDetails);
            case PENTHOUSE ->
                    jpaEntity.setPentHouseDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.PentHouseDetails) jpaDetails);
            case PLOT ->
                    jpaEntity.setPlotDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.PlotDetails) jpaDetails);
            case STUDIO ->
                    jpaEntity.setStudioDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.StudioDetails) jpaDetails);
            case WAREHOUSE ->
                    jpaEntity.setWareHouseDetails((com.ts.marblearch.api.property.infrastructure.persistence.model.details.WareHouseDetails) jpaDetails);
        }
    }
}