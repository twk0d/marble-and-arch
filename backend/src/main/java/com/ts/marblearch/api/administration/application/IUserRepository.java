package com.ts.marblearch.api.administration.application;

import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(Email email);
    void save(User user);
}
