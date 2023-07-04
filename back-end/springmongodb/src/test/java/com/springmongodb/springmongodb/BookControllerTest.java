package com.springmongodb.springmongodb;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.springmongodb.controllers.BookController;
import com.springmongodb.models.Book;
import com.springmongodb.services.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
 
  @InjectMocks
  private BookController bookController;

  @Mock    
  private BookServiceImpl bookService;

  @Test
  public void whenGetAllIsCalled_thenResponseShouldBeOk() {
        List<Book> books = Arrays.asList(
            new Book() {{
                id = "645d1d9fb116312707362206";
                Name = "Design Patterns";
                Author = "Ralph Johnson";
                Category = "Computers";
                Price = new BigDecimal(54.93);
            }},
            new Book() {{
                id = "645d1d9fb116312707362207";
                Name = "Clean Code";
                Author = "Robert C. Martin";
                Category = "Computers";
                Price = new BigDecimal(43.15);
            }}
        );

        Mockito.when(bookService.getAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAll();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void whenGetByIdValidIsCalled_thenResponseShouldBeOk() {
        String bookId = "645d1d9fb116312707362206";
        Optional<Book> book =  Optional.of(new Book() {{
            id = bookId;
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(54.93);
        }});
        Mockito.when(bookService.getById(bookId)).thenReturn(book.get());

        ResponseEntity<Book> response = bookController.getById(bookId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void whenGetByIdInvalidIsCalled_thenResponseShouldBeNotFound() {
        String bookId = "645d1d9fb116312707362207";
        Mockito.when(bookService.getById(bookId)).thenReturn(null);

        ResponseEntity<Book> response = bookController.getById(bookId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void whenCreateIsCalled_thenResponseShouldBeOk() {
        String bookId = "645d1d9fb116312707362206";
        Book book =  new Book() {{
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(54.93);
        }};
        Book newBook =  new Book() {{
            id = bookId;
            Name = book.Name;
            Author = book.Author;
            Category = book.Category;
            Price = book.Price;
        }};
        Mockito.when(bookService.create(book)).thenReturn(newBook);

        ResponseEntity<Book> response = bookController.create(book);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), newBook);
    }

    @Test
    public void whenUpdateValidIsCalled_thenResponseShouldBeNoContent() {
        String bookId = "645d1d9fb116312707362206";
        Book book = new Book() {{
            id = bookId;
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(44.99);
        }};
        Mockito.when(bookService.update(book)).thenReturn(true);

        ResponseEntity<Void> response = bookController.update(book);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void whenUpdateInvalidIsCalled_thenResponseShouldBeNotFound() {
        Book book = new Book() {{
            id = "645d1d9fb116312707362207";
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(44.99);
        }};
        Mockito.when(bookService.update(book)).thenReturn(false);

        ResponseEntity<Void> response = bookController.update(book);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void whenDeleteValidIsCalled_thenResponseShouldBeNoContent() {
        String bookId = "645d1d9fb116312707362206";
        Mockito.when(bookService.delete(bookId)).thenReturn(true);

        ResponseEntity<Void> response = bookController.delete(bookId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void whenDeleteInvalidIsCalled_thenResponseShouldBeNotFound() {
        String bookId = "645d1d9fb116312707362207";
        Mockito.when(bookService.delete(bookId)).thenReturn(false);

        ResponseEntity<Void> response = bookController.delete(bookId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
}
