package com.javadev.bookmanager.dto;

import com.javadev.bookmanager.entities.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotBlank(message = "Name is required")
    private String name;

    public CategoryDTO(Category category) {
        this.name = category.getName();
    }

    public Category transformToObject(){
        return new Category(this.name);
    }

}
