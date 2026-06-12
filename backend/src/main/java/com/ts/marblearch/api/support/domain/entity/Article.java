package com.ts.marblearch.api.support.domain.entity;

import com.ts.marblearch.api.sharedKernel.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Article extends AggregateRoot {
    private final UUID id;
    private String title;
    private String content;
    private String category;
}
