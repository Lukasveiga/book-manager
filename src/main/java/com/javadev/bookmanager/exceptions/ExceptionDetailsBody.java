package com.javadev.bookmanager.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionDetailsBody(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
