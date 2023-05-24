package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.request.BookPostRequestBody;
import com.javadev.bookmanager.service.book.BookService;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService service;

    private Book bookTest;

    private Author authorTest;

    private Category categoryTest;

    @BeforeEach
    void setUp() {
        bookTest = GenerateBookAuthorCategory.generateBookTest();
        authorTest = GenerateBookAuthorCategory.generateAuthorTest();
        categoryTest = GenerateBookAuthorCategory.generateCategoryTest();
        bookTest.addAuthor(authorTest);
        bookTest.addCategory(categoryTest);
    }


    @Test
    public void findAll_ReturnListOfBooks_WhenSuccessful() {
        when(service.listAll())
                .thenReturn(List.of(new BookDTO(bookTest)));
        List<BookDTO> books = controller.findAll().getBody();
        assertThat(books).isNotNull().isNotEmpty().hasSize(1);
        assertThat(books.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void findByTitle_ReturnBookByTitle_WhenSuccessful() {
        String bookTitle = bookTest.getTitle();
        when(service.findByTitle(anyString()))
                .thenReturn(new BookDTO(bookTest));
        BookDTO book = controller.findByTitle(bookTitle).getBody();
        assertThat(book).isNotNull().usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void addBook_ShouldSaveBookAndReturnBookSaved_WhenSuccessful() {
        BookPostRequestBody bookRequest = GenerateBookAuthorCategory.generateBookRequestBody();
        when(service.save(any(BookPostRequestBody.class)))
                .thenReturn(new BookDTO(bookTest));
        BookDTO bookSaved = controller.addBook(bookRequest).getBody();
        assertThat(bookSaved).isNotNull().usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void updateBook_ShouldUpdateBookAndReturnBookUpdated_WhenSuccessful() {
        BookPostRequestBody bookRequest = GenerateBookAuthorCategory.generateBookRequestBody();
        long id = 1;
        when(service.update(id, bookRequest))
                .thenReturn(new BookDTO(bookTest));
        BookDTO bookUpdated = controller.updateBook(id, bookRequest).getBody();
        assertThat(bookUpdated).isNotNull().usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void addAuthorToABook_ShouldInsertAuthorIntoABookAndReturnBookUpdated_WhenSuccessful(){
        String bookTitle = bookTest.getTitle();
        String authorTitle = authorTest.getName();
        when(service.insertAuthor(bookTitle, authorTitle))
                .thenReturn(new BookDTO(bookTest));
        BookDTO bookWithAuthor = controller.addAuthorToABook(bookTitle, authorTitle).getBody();
        assertThat(bookWithAuthor).isNotNull().usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void addCategoryToABook_ShouldInsertCategoryIntoABookAndReturnBookUpdated_WhenSuccessful() {
        String bookTitle = bookTest.getTitle();
        String categoryName = categoryTest.getName();
        when(service.insertCategory(bookTitle, categoryName))
                .thenReturn(new BookDTO(bookTest));
        BookDTO bookWithCategory = controller.addCategoryToABook(bookTitle, categoryName).getBody();
        assertThat(bookWithCategory).isNotNull().usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void deleteBook_ShouldDeleteBookAndReturnVoid_WhenSuccessful() {
        long id = 1;
        doNothing().when(service).delete(id);
        assertThatCode(() -> controller.deleteBook(id)).doesNotThrowAnyException();
    }

}