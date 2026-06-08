package com.ts.marblearch.api.administration.webAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.marblearch.api.administration.webAdapter.responses.AdminDashboardDTO;
import com.ts.marblearch.api.property.application.IPropertyRepository;
import com.ts.marblearch.api.property.domain.entity.property.Property;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Address;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Money;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.PropertyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.ts.marblearch.api.MarbleArchApplication.class)
@AutoConfigureMockMvc
class AdminDashboardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPropertyRepository propertyRepository;

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE audit_logs CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE properties CASCADE");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should return empty dashboard statistics when database is empty")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmptyDashboardStatisticsWhenDatabaseIsEmpty() throws Exception {
        var mvcResult = mockMvc.perform(get("/api/v1/administration/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProperties").value(0))
                .andExpect(jsonPath("$.activeProperties").value(0))
                .andExpect(jsonPath("$.inactiveProperties").value(0))
                .andExpect(jsonPath("$.totalMarketValue").value(comparesEqualTo(BigDecimal.ZERO), BigDecimal.class));
    }

    @Test
    @DisplayName("Should return dashboard statistics for admin")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnDashboardStatisticsForAdmin() throws Exception {
        // Arrange: Create some properties
        createProperty(PropertyType.HOUSE, new BigDecimal("1000.00"), true);
        createProperty(PropertyType.OFFICE, new BigDecimal("2000.00"), true);
        createProperty(PropertyType.HOUSE, new BigDecimal("500.00"), false);

        // Act & Assert
        var mvcResult = mockMvc.perform(get("/api/v1/administration/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProperties").value(3))
                .andExpect(jsonPath("$.activeProperties").value(2))
                .andExpect(jsonPath("$.inactiveProperties").value(1))
                .andExpect(jsonPath("$.totalMarketValue").value(comparesEqualTo(new BigDecimal("3000.00")), BigDecimal.class))
                .andExpect(jsonPath("$.distributionByType.HOUSE").value(2))
                .andExpect(jsonPath("$.distributionByType.OFFICE").value(1));
    }

    @Test
    @DisplayName("Should return dashboard statistics for broker")
    @WithMockUser(roles = "BROKER")
    void shouldReturnDashboardStatisticsForBroker() throws Exception {
        // Arrange: Create some properties
        createProperty(PropertyType.STUDIO, new BigDecimal("800.00"), true);

        // Act & Assert
        var mvcResult = mockMvc.perform(get("/api/v1/administration/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should forbid dashboard access for non-admin users")
    @WithMockUser(roles = "CLIENT")
    void shouldForbidDashboardAccessForNonAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/administration/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private void createProperty(PropertyType type, BigDecimal price, boolean active) {
        Property property = Property.fromPersistence(
                UUID.randomUUID(),
                active,
                new ArrayList<>(),
                type,
                new Address("Street", "123", "Neighborhood", "City", "ST", "12345678", null),
                new Money(price, com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Currency.BRL),
                null
        );
        propertyRepository.save(property);
    }
}
