package com.ts.marblearch.api.administration.application;

public interface IPasswordHasher {
    String hash(String password);
    boolean matches(String password, String hash);
}
