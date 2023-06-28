package com.springmongodb.springmongodb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.springmongodb.models.Book;
import com.springmongodb.repositories.BookRepository;
import com.springmongodb.services.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void whenGetAllIsCalled_thenBooksShouldBeFound() {
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

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> found = bookService.getAll();

        Assertions.assertTrue(found.equals(books));
    }

    @Test
    public void whenGetByIdValidIsCalled_thenBookShouldBeFound() {
        String bookId = "645d1d9fb116312707362206";
        Optional<Book> book =  Optional.of(new Book() {{
            id = bookId;
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(54.93);
        }});
        Mockito.when(bookRepository.findById(bookId)).thenReturn(book);

        Book found = bookService.getById(bookId);

        Assertions.assertTrue(found.equals(book.get()));
    }

    @Test
    public void whenGetByIdInvalidIsCalled_thenBookShouldBeNotFound() {
        String bookId = "645d1d9fb116312707362207";
        Optional<Book> book =  Optional.empty();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(book);

        Book found = bookService.getById(bookId);

        Assertions.assertNull(found);
    }

    @Test
    public void whenGetByCriteriaValidIsCalled_thenBooksShouldBeFound() {
        String criteria = "Category";
        String search = "Computers";
        List<Book> books = Arrays.asList(
            new Book() {{
                id = "645d1d9fb116312707362206";
                Name = "Design Patterns";
                Author = "Ralph Johnson";
                Category = search;
                Price = new BigDecimal(54.93);
            }},
            new Book() {{
                id = "645d1d9fb116312707362207";
                Name = "Clean Code";
                Author = "Robert C. Martin";
                Category = search;
                Price = new BigDecimal(43.15);
            }}
        );
        Query query = new Query();
        query.addCriteria(Criteria.where(criteria).regex(String.format(".*%s.*", search), "i"));

        Mockito.when(mongoTemplate.find(query, Book.class)).thenReturn(books);

        List<Book> found = bookService.getByCriteria(criteria, search);

        Assertions.assertTrue(found.equals(books));
    }

    @Test
    public void whenGetByCriteriaInvalidIsCalled_thenBooksShouldBeNotFound() {
        String criteria = "Category";
        String search = "Programming";
        List<Book> books = new ArrayList<>();

        Query query = new Query();
        query.addCriteria(Criteria.where(criteria).regex(String.format(".*%s.*", search), "i"));

        Mockito.when(mongoTemplate.find(query, Book.class)).thenReturn(books);

        List<Book> found = bookService.getByCriteria(criteria, search);

        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    public void whenCreateIsCalled_thenBookShouldBeSave() {
        String bookId = "645d1d9fb116312707362206";        
        Book book = new Book() {{
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(54.93);
        }};
        Book bookCreated = new Book() {{
            id = bookId;
            Name = book.Name;
            Author = book.Author;
            Category = book.Category;
            Price = book.Price;
        }};
        Mockito.when(bookRepository.save(book)).thenReturn(bookCreated);

        Book bookNew = bookService.create(book);

        Assertions.assertTrue(bookNew.equals(bookCreated));
    }

    @Test
    public void whenUpdateValidIsCalled_thenReturnShouldBeTrue() {
        String bookId = "645d1d9fb116312707362206";
        Book book = new Book() {{
            id = bookId;
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(44.99);
        }};
        Optional<Book> updatedBook =  Optional.of(book);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(updatedBook);
        Mockito.when(bookRepository.save(updatedBook.get())).thenReturn(book);

        Boolean updated = bookService.update(book);

        Assertions.assertTrue(updated);
    }

    @Test
    public void whenUpdateInvalidIsCalled_thenReturnShouldBeFalse() {
        String bookId = "645d1d9fb116312707362206";
        Book book = new Book() {{
            id = "645d1d9fb116312707362207";
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(44.99);
        }};
        Optional<Book> notFoundBook =  Optional.empty();
        Mockito.lenient().when(bookRepository.findById(bookId)).thenReturn(notFoundBook);

        Boolean updated = bookService.update(book);

        Assertions.assertFalse(updated);
    }

    @Test
    public void whenDeleteValidIsCalled_thenReturnShouldBeTrue() {
        String bookId = "645d1d9fb116312707362206";
        Book book = new Book() {{
            id = bookId;
            Name = "Design Patterns";
            Author = "Ralph Johnson";
            Category = "Computers";
            Price = new BigDecimal(44.99);
        }};
        Optional<Book> deletedBook =  Optional.of(book);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(deletedBook);
        Mockito.doNothing().when(bookRepository).deleteById(bookId);

        Boolean deleted = bookService.delete(bookId);

        Assertions.assertTrue(deleted);
    }

    @Test
    public void whenDeleteInvalidIsCalled_thenReturnShouldBeFalse() {
        String bookId = "645d1d9fb116312707362207";
        Optional<Book> deletedBook =  Optional.empty();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(deletedBook);

        Boolean deleted = bookService.delete(bookId);

        Assertions.assertFalse(deleted);
    }
}
