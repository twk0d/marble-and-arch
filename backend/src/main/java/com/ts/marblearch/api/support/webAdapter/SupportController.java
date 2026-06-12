package com.ts.marblearch.api.support.webAdapter;

import com.ts.marblearch.api.support.infrastructure.persistence.ArticleJpaRepository;
import com.ts.marblearch.api.support.infrastructure.persistence.model.ArticleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/${api.version}/support")
@RequiredArgsConstructor
public class SupportController {

    private final ArticleJpaRepository articleRepository;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleJpaEntity>> getAllArticles() {
        return ResponseEntity.ok(articleRepository.findAll());
    }
}
