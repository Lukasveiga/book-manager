package com.javadev.bookmanager.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestBody {

    @NotBlank(message = "Name is required.")
    private String name;

    private int year;

    private int pages;
}
