package com.distribuida.repository;

import com.distribuida.db.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
				extends MongoRepository<Book, String> {
}
