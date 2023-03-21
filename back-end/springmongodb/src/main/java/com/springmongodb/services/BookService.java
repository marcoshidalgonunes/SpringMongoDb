package com.springmongodb.services;

import java.util.List;

import com.springmongodb.models.Book;

public interface BookService {
    Book create(Book book);

    Boolean delete(String id);

    List<Book> getAll();

    List<Book> getByCriteria(String criteria, String search);

    Book getById(String id);

    Boolean update(Book book);
}
