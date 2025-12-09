package com.farabi.taskmanager.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class ApiErrorResponse {
    private Instant timestamp;
    private String error;
    private String message;
    private String path;

    private Map<String, String> errors;
}
