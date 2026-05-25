package com.ts.marblearch.api.administration.domain.events;

import com.ts.marblearch.api.administration.domain.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRegisteredEvent {
    private final User user;
}
