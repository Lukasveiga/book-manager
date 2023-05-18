package com.javadev.bookmanager.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionDetails(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
