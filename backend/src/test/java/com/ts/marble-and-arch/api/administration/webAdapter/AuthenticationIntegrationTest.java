package com.ts.marblearch.api.administration.webAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.marblearch.api.administration.application.IUserRepository;
import com.ts.marblearch.api.administration.webAdapter.requests.LoginRequest;
import com.ts.marblearch.api.administration.webAdapter.requests.RegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.ts.marblearch.api.MarbleArchApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("dev")
@Transactional
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @DisplayName("Should register and login successfully")
    void shouldRegisterAndLoginSuccessfully() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                "Test",
                "User",
                "test@marble-and-arch.com",
                "password123",
                "CLIENT"
        );

        // 1. Register
        mockMvc.perform(post("/api/v1/administration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        // 2. Login
        LoginRequest loginRequest = new LoginRequest("test@marble-and-arch.com", "password123");
        mockMvc.perform(post("/api/v1/administration/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should fail login with wrong password")
    void shouldFailLoginWithWrongPassword() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                "Test", "Wrong", "wrong@marble-and-arch.com", "password123", "CLIENT"
        );

        mockMvc.perform(post("/api/v1/administration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        LoginRequest loginRequest = new LoginRequest("wrong@marble-and-arch.com", "wrongpassword");
        mockMvc.perform(post("/api/v1/administration/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnprocessableEntity());
    }
}
