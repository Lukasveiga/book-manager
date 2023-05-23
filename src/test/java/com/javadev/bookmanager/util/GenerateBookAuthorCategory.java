package com.javadev.bookmanager.util;

import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.request.AuthorPostRequestBody;
import com.javadev.bookmanager.request.BookPostRequestBody;

public class GenerateBookAuthorCategory {

    private static final String AUTHOR_TEST_NAME = "Author Test Name";
    private static final String AUTHOR_TEST_ABOUT = "Author Test Description";

    private static final String BOOK_TEST_NAME = "Book Test Title";

    private static final int BOOK_TEST_YEAR = 1999;

    private static final int BOOK_TEST_PAGES = 999;

    private static final String BOOK_TEST_LANGUAGE = "portguês-br";

    private static final String BOOK_TEST_IMAGE = "www.google-images.com.br/book.png";

    private static final String CATEGORY_TEST_NAME = "Category Test Name";

    public static Author generateAuthorTest() {
        return new Author(AUTHOR_TEST_NAME, AUTHOR_TEST_ABOUT);
    }

    public static Book generateBookTest() {
        return new Book(BOOK_TEST_NAME, BOOK_TEST_YEAR, BOOK_TEST_PAGES, BOOK_TEST_LANGUAGE, BOOK_TEST_IMAGE);
    }

    public static Category generateCategoryTest(){
        return new Category(CATEGORY_TEST_NAME);
    }

    public static AuthorPostRequestBody generateAuthorRequestBody(){
        return new AuthorPostRequestBody(AUTHOR_TEST_NAME, AUTHOR_TEST_ABOUT);
    }

    public static BookPostRequestBody generateBookRequestBody(){
        return new BookPostRequestBody(BOOK_TEST_NAME, BOOK_TEST_YEAR, BOOK_TEST_PAGES, BOOK_TEST_LANGUAGE, BOOK_TEST_IMAGE);
    }

    public static CategoryDTO generateCategoryDTO(){
        return new CategoryDTO(CATEGORY_TEST_NAME);
    }
}
