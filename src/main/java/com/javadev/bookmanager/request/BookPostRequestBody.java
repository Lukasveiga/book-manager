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
public class BookPostRequestBody {

    @NotBlank(message = "Name is required.")
    private String title;

    private int year;

    private int pages;

    private String language;

    private String image;


    public Book transformToObject() {
        return new Book(this.title, this.year, this.pages, this.language, this.image);
    }
}
