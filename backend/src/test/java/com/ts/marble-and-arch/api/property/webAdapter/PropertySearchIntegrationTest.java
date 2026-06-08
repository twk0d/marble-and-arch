package com.ts.marblearch.api.property.webAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Address;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.context.annotation.Import;

@SpringBootTest(classes = com.ts.marblearch.api.MarbleArchApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("dev")
@Transactional
class PropertySearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPropertyRepository propertyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should return property summary in search results")
    void shouldReturnPropertySummaryInSearchResults() throws Exception {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("Street", "123", "Neighborhood", "City", "ST", "12345678", null);
        Money price = new Money(new BigDecimal("1500.00"), com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency.BRL);
        
        Property property = Property.fromPersistence(
                propertyId,
                true,
                new ArrayList<>(),
                PropertyType.HOUSE,
                address,
                price,
                null // No details for summary test
        );
        propertyRepository.save(property);
        entityManager.flush();
        entityManager.clear();

        PageableFilters filters = new PageableFilters(); // Empty filters

        // Act & Assert
        var mvcResult = mockMvc.perform(get("/api/v1/property-management/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filters)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(propertyId.toString()))
                .andExpect(jsonPath("$.content[0].type").value("HOUSE"))
                .andExpect(jsonPath("$.content[0].city").value("City"))
                .andExpect(jsonPath("$.content[0].state").value("ST"))
                .andExpect(jsonPath("$.content[0].priceAmount").value(1500.00))
                .andExpect(jsonPath("$.content[0].priceCurrency").value("BRL"));
    }
}
