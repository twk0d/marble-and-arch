package com.ts.marblearch.api.administration.infrastructure.persistence;

import com.ts.marblearch.api.administration.application.IUserRepository;
import com.ts.marblearch.api.administration.domain.entity.user.User;
import com.ts.marblearch.api.administration.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.ts.marblearch.api.administration.infrastructure.persistence.model.UserJpaEntity;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.address()).map(mapper::toDomain);
    }

    @Override
    public void save(User user) {
        UserJpaEntity jpaEntity = mapper.toJpaEntity(user);
        jpaRepository.save(jpaEntity);
    }
}
