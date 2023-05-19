package com.javadev.bookmanager.request;

import com.javadev.bookmanager.entities.Book;
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

    public Book transformToObject() {
        return new Book(this.name, this.year, this.pages);
    }
}
