package com.springmongodb;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springmongodb.controllers.BookController;
import com.springmongodb.repositories.BookRepository;
import com.springmongodb.services.BookService;

@SpringBootTest
class SpringmongodbApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookController bookContoller;

	@Test
	void contextLoads() {
		assertNotNull(bookRepository);
		assertNotNull(bookService);
		assertNotNull(bookContoller);

	}
}
