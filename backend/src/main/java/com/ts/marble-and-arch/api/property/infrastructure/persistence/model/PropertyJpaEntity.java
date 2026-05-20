package com.ts.marblearch.api.property.infrastructure.persistence.model;

import com.ts.marblearch.api.property.infrastructure.persistence.model.details.*;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "properties", indexes = {
    @Index(name = "idx_properties_active_price", columnList = "active, price_amount"),
    @Index(name = "idx_properties_type", columnList = "type"),
    @Index(name = "idx_properties_city", columnList = "city")
})
@NoArgsConstructor
public class PropertyJpaEntity {

    @Id
    private UUID id;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    // Price
    @Column(name = "price_amount", nullable = false)
    private BigDecimal priceAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency", nullable = false)
    private Currency priceCurrency;

    // Address
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String complement;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageJpaEntity> images = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "house_details_id")
    private HouseDetails houseDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "condominium_house_details_id")
    private CondominiumHouseDetails condominiumHouseDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "condominium_plot_details_id")
    private CondominiumPlotDetails condominiumPlotDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "country_house_details_id")
    private CountryHouseDetails countryHouseDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "office_details_id")
    private OfficeDetails officeDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "others_details_id")
    private OthersDetails othersDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "penthouse_details_id")
    private PentHouseDetails pentHouseDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "plot_details_id")
    private PlotDetails plotDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "studio_details_id")
    private StudioDetails studioDetails;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "warehouse_details_id")
    private WareHouseDetails wareHouseDetails;

    public PropertyJpaEntity(UUID id, boolean active, PropertyType type) {
        this.id = id;
        this.active = active;
        this.type = type;
    }

    public void addImage(ImageJpaEntity image) {
        this.images.add(image);
        image.setProperty(this);
    }

    public void removeImage(ImageJpaEntity image) {
        this.images.remove(image);
        image.setProperty(null);
    }
}
