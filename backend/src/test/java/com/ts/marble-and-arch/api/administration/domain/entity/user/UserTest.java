package com.ts.marblearch.api.administration.domain.entity.user;

import com.ts.marblearch.api.administration.domain.entity.user.Role;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Should create user successfully when data is valid")
    void shouldCreateUserSuccessfully() {
        UUID id = UUID.randomUUID();
        Name name = new Name("John", "Doe");
        Email email = new Email("john@example.com");
        String password = "securePassword123";
        Role role = Role.CLIENT;

        User user = new User(id, name, email, password, role);

        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertTrue(user.isActive());
    }

    @Test
    @DisplayName("Should throw exception for invalid email format")
    void shouldThrowExceptionForInvalidEmail() {
        assertThrows(BussinessRuleValidationException.class, () -> new Email("invalid-email"));
    }

    @Test
    @DisplayName("Should correctly deactivate and activate user")
    void shouldToggleUserStatus() {
        User user = new User(UUID.randomUUID(), new Name("A", "B"), new Email("a@b.com"), "pass", Role.CLIENT);
        
        user.deactivate();
        assertFalse(user.isActive());

        user.activate();
        assertTrue(user.isActive());
    }
}
