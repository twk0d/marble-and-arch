package com.ts.marblearch.api.property.domain.entity.property;

import com.ts.marblearch.api.property.domain.entity.image.Image;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    private Address createDefaultAddress() {
        return new Address("Street", "123", "Neighborhood", "City", "State", "12345678", "");
    }

    private Money createDefaultPrice() {
        return new Money(BigDecimal.valueOf(100000), Currency.BRL);
    }

    @Test
    @DisplayName("Should create a property successfully when data is valid")
    void shouldCreatePropertySuccessfully() {
        UUID id = UUID.randomUUID();
        PropertyType type = PropertyType.HOUSE;
        Object details = new Object();
        CompletableFuture<UUID> future = new CompletableFuture<>();

        Property property = new Property(id, true, new ArrayList<>(), type, createDefaultAddress(), createDefaultPrice(), details, future);

        assertNotNull(property);
        assertEquals(id, property.getId());
        assertEquals(type, property.getType());
        assertTrue(property.isActive());
    }

    @Test
    @DisplayName("Should throw exception when creating property with null ID")
    void shouldThrowExceptionWhenIdIsNull() {
        assertThrows(BussinessRuleValidationException.class, () -> 
            new Property(null, true, new ArrayList<>(), PropertyType.HOUSE, createDefaultAddress(), createDefaultPrice(), new Object(), new CompletableFuture<>())
        );
    }

    @Test
    @DisplayName("Should throw exception when creating property with null details")
    void shouldThrowExceptionWhenDetailsAreNull() {
        assertThrows(BussinessRuleValidationException.class, () -> 
            new Property(UUID.randomUUID(), true, new ArrayList<>(), PropertyType.HOUSE, createDefaultAddress(), createDefaultPrice(), null, new CompletableFuture<>())
        );
    }

    @Test
    @DisplayName("Should correctly toggle property status")
    void shouldToggleStatus() {
        Property property = new Property(UUID.randomUUID(), true, new ArrayList<>(), PropertyType.HOUSE, createDefaultAddress(), createDefaultPrice(), new Object(), new CompletableFuture<>());
        
        property.disable(new CompletableFuture<>());
        assertFalse(property.isActive());

        property.enable(new CompletableFuture<>());
        assertTrue(property.isActive());
    }

    @Test
    @DisplayName("Should add image to property successfully")
    void shouldAddImageSuccessfully() {
        Property property = new Property(UUID.randomUUID(), true, new ArrayList<>(), PropertyType.HOUSE, createDefaultAddress(), createDefaultPrice(), new Object(), new CompletableFuture<>());
        
        property.addImage("http://test.com/img.jpg", "Test image");
        
        assertEquals(1, property.getImages().size());
        assertEquals("http://test.com/img.jpg", property.getImages().get(0).getUrl());
    }

    @Test
    @DisplayName("Should throw exception when adding image with invalid URL")
    void shouldThrowExceptionWhenImageUrlIsInvalid() {
        Property property = new Property(UUID.randomUUID(), true, new ArrayList<>(), PropertyType.HOUSE, createDefaultAddress(), createDefaultPrice(), new Object(), new CompletableFuture<>());
        
        assertThrows(BussinessRuleValidationException.class, () -> property.addImage("", "Test"));
        assertThrows(BussinessRuleValidationException.class, () -> property.addImage(null, "Test"));
    }
}
