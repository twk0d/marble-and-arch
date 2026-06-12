package com.ts.marblearch.api.support.infrastructure.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
public class ArticleJpaEntity {
    @Id
    private UUID id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String category;

    public ArticleJpaEntity(UUID id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
