package com.ts.marblearch.api.sharedKernel.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseEnvelope<T> {
    private boolean success;
    private T data;
    private ErrorDetails error;
    private String message;

    public static <T> ApiResponseEnvelope<T> error(String code, String message) {
        ApiResponseEnvelope<T> envelope = new ApiResponseEnvelope<>();
        envelope.setSuccess(false);
        envelope.setError(new ErrorDetails(code));
        envelope.setMessage(message);
        return envelope;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetails {
        private String code;
    }
}
