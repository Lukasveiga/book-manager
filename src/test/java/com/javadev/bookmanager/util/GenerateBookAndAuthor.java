package com.javadev.bookmanager.util;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;

public class GenerateBookAndAuthor {

    public static Author generateAuthorTest() {
        return new Author("Author Test Name", "Author Test Description");
    }

    public static Book generateBookTest() {
        return new Book("Book Test Title", 9999, 999);
    }
}
