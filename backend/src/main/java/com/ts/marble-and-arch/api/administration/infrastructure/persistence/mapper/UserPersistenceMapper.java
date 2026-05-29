package com.ts.marblearch.api.administration.infrastructure.persistence.mapper;

import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.UserJpaEntity;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Name;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getName().firstName(),
                user.getName().lastName(),
                user.getEmail().address(),
                user.getPassword(),
                user.getRole(),
                user.isActive()
        );
    }

    public User toDomain(UserJpaEntity jpaEntity) {
        return new User(
                jpaEntity.getId(),
                new Name(jpaEntity.getFirstName(), jpaEntity.getLastName()),
                new Email(jpaEntity.getEmail()),
                jpaEntity.getPassword(),
                jpaEntity.getRole(),
                jpaEntity.isActive()
        );
    }
}
