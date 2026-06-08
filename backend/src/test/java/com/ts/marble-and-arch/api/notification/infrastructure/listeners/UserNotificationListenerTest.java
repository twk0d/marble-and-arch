package com.ts.marblearch.api.notification.infrastructure.listeners;

import com.ts.marblearch.api.administration.domain.entity.user.Role;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.domain.events.UserRegisteredEvent;
import com.ts.marblearch.api.notification.application.IEmailSender;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserNotificationListenerTest {

    @Mock
    private IEmailSender emailSender;

    @InjectMocks
    private UserNotificationListener listener;

    @Test
    @DisplayName("Should send welcome email when user is registered")
    void shouldSendWelcomeEmail() {
        User user = new User(UUID.randomUUID(), new Name("Test", "User"), new Email("test@example.com"), "pass", Role.CLIENT);
        UserRegisteredEvent event = new UserRegisteredEvent(user);

        listener.handleUserRegistered(event);

        verify(emailSender).sendTemplateEmail(
                eq(user.getEmail()),
                anyString(),
                eq("welcome-email"),
                anyMap()
        );
    }
}
