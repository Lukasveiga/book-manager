package com.javadev.bookmanager.util;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.request.AuthorPostRequestBody;
import com.javadev.bookmanager.request.BookPostRequestBody;

public class GenerateBookAndAuthor {

    private static final String AUTHOR_TEST_NAME = "Author Test Name";
    private static final String AUTHOR_TEST_ABOUT = "Author Test Description";

    private static final String BOOK_TEST_NAME = "Book Test Title";

    private static final int BOOK_TEST_YEAR = 1999;

    private static final int BOOK_TEST_PAGES = 999;

    public static Author generateAuthorTest() {
        return new Author(AUTHOR_TEST_NAME, AUTHOR_TEST_ABOUT);
    }

    public static Book generateBookTest() {
        return new Book(BOOK_TEST_NAME, BOOK_TEST_YEAR, BOOK_TEST_PAGES);
    }

    public static AuthorPostRequestBody generateAuthorRequestBody(){
        return new AuthorPostRequestBody(AUTHOR_TEST_NAME, AUTHOR_TEST_ABOUT);
    }

    public static BookPostRequestBody generateBookRequestBody(){
        return new BookPostRequestBody(BOOK_TEST_NAME, BOOK_TEST_YEAR, BOOK_TEST_PAGES);
    }
}
