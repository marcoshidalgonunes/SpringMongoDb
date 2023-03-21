package com.springmongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springmongodb.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {    
}
