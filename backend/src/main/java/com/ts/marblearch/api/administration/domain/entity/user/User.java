package com.ts.marblearch.api.administration.domain.entity.user;

import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import com.ts.marblearch.api.sharedKernel.domain.exceptions.BussinessRuleValidationException;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class User extends AggregateRoot {
    private final UUID id;
    private final Name name;
    private final Email email;
    private String password;
    private final Role role;
    private boolean active;

    public User(UUID id, Name name, Email email, String password, Role role) {
        if (id == null) throw new BussinessRuleValidationException("User ID cannot be null");
        if (name == null) throw new BussinessRuleValidationException("User name cannot be null");
        if (email == null) throw new BussinessRuleValidationException("User email cannot be null");
        if (password == null || password.isBlank()) throw new BussinessRuleValidationException("User password cannot be empty");
        if (role == null) throw new BussinessRuleValidationException("User role cannot be null");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}
