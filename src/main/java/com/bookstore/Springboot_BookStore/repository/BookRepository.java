package com.bookstore.Springboot_BookStore.repository;

import com.bookstore.Springboot_BookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
