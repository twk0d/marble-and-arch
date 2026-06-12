package com.ts.marblearch.api.support.infrastructure.persistence;

import com.ts.marblearch.api.support.infrastructure.persistence.model.ArticleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleJpaRepository extends JpaRepository<ArticleJpaEntity, UUID> {
}
