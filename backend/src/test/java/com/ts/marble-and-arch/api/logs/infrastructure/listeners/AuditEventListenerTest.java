package com.ts.marblearch.api.logs.infrastructure.listeners;

import com.ts.marblearch.api.administration.domain.entity.user.Role;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.domain.events.UserRegisteredEvent;
import com.ts.marblearch.api.logs.infrastructure.persistence.AuditLogJpaRepository;
import com.ts.marblearch.api.logs.infrastructure.persistence.model.AuditLogJpaEntity;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditEventListenerTest {

    @Mock
    private AuditLogJpaRepository auditRepository;

    @InjectMocks
    private AuditEventListener listener;

    @Test
    @DisplayName("Should save audit log when user is registered")
    void shouldSaveLogOnUserRegistered() {
        User user = new User(UUID.randomUUID(), new Name("Log", "Test"), new Email("log@test.com"), "pass", Role.ADMIN);
        UserRegisteredEvent event = new UserRegisteredEvent(user);

        listener.handleUserRegistered(event);

        verify(auditRepository).save(any(AuditLogJpaEntity.class));
    }
}
