package com.javadev.bookmanager.request;

import com.javadev.bookmanager.entities.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestBody {

    @NotBlank(message = "Name is required.")
    private String name;

    private String about;

    public Author transformToObject() {
        return new Author(this.name, this.about);
    }
}
