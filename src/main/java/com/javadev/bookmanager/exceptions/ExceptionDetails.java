package com.javadev.bookmanager.exceptions;

import java.time.LocalDateTime;

public record ExceptionDetails(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
