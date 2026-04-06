package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.dto.BookDTO;
import com.bookstore.Springboot_BookStore.exception.ResourceNotFoundException;
import com.bookstore.Springboot_BookStore.model.Book;
import com.bookstore.Springboot_BookStore.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {


    private  final BookRepository bookRepository;
    private final ModelMapper modelMapper;


    public BookDTO createBook(BookDTO bookDTO) {
        // Convert DTO to entity
        Book book = modelMapper.map(bookDTO, Book.class);
        // Save entity to the database
        Book savedBook = bookRepository.save(book);
        // Convert saved entity back to DTO and return
        return modelMapper.map(savedBook, BookDTO.class);

    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList()); //
    }
    public  BookDTO getById(Long id) {
        Book bookId = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return modelMapper.map(bookId, BookDTO.class);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book Not Found"));

        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthor() != null) {
            book.setAuthor(bookDTO.getAuthor());
        }
        if (bookDTO.getPrice() != null) {
            book.setPrice(bookDTO.getPrice());
        }
        if (bookDTO.getStock() != null) {
            book.setStock(bookDTO.getStock());
        }

        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book Not Found");
        }
        bookRepository.deleteById(id);
    }



}
